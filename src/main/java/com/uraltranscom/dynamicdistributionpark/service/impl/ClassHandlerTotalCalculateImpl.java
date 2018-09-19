package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.additional_model.WagonRateAndTariff;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalRouteInfo;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import com.uraltranscom.dynamicdistributionpark.service.additional.PrepareDateForInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Класс для расчета доходности и остальных показателей
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 *
 */

@Service
@Component
public class ClassHandlerTotalCalculateImpl {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerTotalCalculateImpl.class);

    @Autowired
    private ClassHandlerInsertRateOrTariffImpl classHandlerInsertRateOrTariff;
    @Autowired
    private GetListOfRoutesImpl getListOfRoutes;

    private Map<String, WagonFinalInfo> newMapWagonFinalInfo = new HashMap<>();
    private Map<Integer, List<Object>> finalCountOrdersWithVolume = new TreeMap<>();

    public void updateMap(Map<String, WagonFinalInfo> map, String wagons, String rates, String tariffs, String routes) {
        newMapWagonFinalInfo.clear();
        List<WagonRateAndTariff> listRateAndTariff = PrepareDateForInsert.fillListForUpdate(wagons, rates, tariffs, routes);
        Map<String, WagonFinalInfo> tempNewMap = new HashMap<>();
        //TODO Изменить на более изящно
        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
            String numberWagon = _map.getKey();
            List<WagonFinalRouteInfo> list = new ArrayList<>();
            for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                list.add(new WagonFinalRouteInfo(_map.getValue().getListRouteInfo().get(i).getCountCircleDays(),
                        _map.getValue().getListRouteInfo().get(i).getDistanceEmpty(),
                        _map.getValue().getListRouteInfo().get(i).getCurrentNameOfStationOfWagon(),
                        _map.getValue().getListRouteInfo().get(i).getCurrentKeyOfStationOfWagon(),
                        _map.getValue().getListRouteInfo().get(i).getNameOfStationDepartureOfWagon(),
                        _map.getValue().getListRouteInfo().get(i).getKeyOfStationDepartureOfWagon(),
                        _map.getValue().getListRouteInfo().get(i).getRoute(),
                        _map.getValue().getListRouteInfo().get(i).getCargo(),
                        _map.getValue().getListRouteInfo().get(i).getCargoType(),
                        _map.getValue().getListRouteInfo().get(i).isEmpty(),
                        _map.getValue().getListRouteInfo().get(i).isLoadingRateFromDB(),
                        _map.getValue().getListRouteInfo().get(i).isLoadingTariffFromDB()));
            }
            WagonFinalInfo wagonFinalInfo = new WagonFinalInfo(
                    _map.getValue().getNumberOfWagon(),
                    list);
            tempNewMap.put(numberWagon, wagonFinalInfo);
        }
        logger.debug("listRateAndTariff: {}", listRateAndTariff);
        for (Map.Entry<String, WagonFinalInfo> _map : tempNewMap.entrySet()) {
            for (WagonRateAndTariff list : listRateAndTariff) {
                if (_map.getKey().equals(list.getNumberOfWagon())) {
                    for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                        if (_map.getValue().getListRouteInfo().get(i).getRoute().getNumberOrder().equals(list.getRouteId())) {
                            _map.getValue().getListRouteInfo().get(i).setRate(list.getRate());
                        }
                        if (_map.getValue().getListRouteInfo().get(i).getRoute().getNumberOrder().equals(list.getRouteId())) {
                            _map.getValue().getListRouteInfo().get(i).setTariff(list.getTariff());
                        }
                    }
                }
            }
        }
        newMapWagonFinalInfo.putAll(tempNewMap);
        logger.debug("newMapWagonFinalInfo: {}, map: {}", newMapWagonFinalInfo, map);
        classHandlerInsertRateOrTariff.insertDate(newMapWagonFinalInfo, map);
        calculateYield(newMapWagonFinalInfo);
    }

    public void calculateYield(Map<String, WagonFinalInfo> map) {
        finalCountOrdersWithVolume.clear();
        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
            double sumRate = 0.00;
            double sumTariff = 0.00;
            int sumCountDays = 0;
            int key = changeValueVolume(_map.getValue().getListRouteInfo().get(_map.getValue().getListRouteInfo().size() - 1).getRoute().getVolumePeriod().getVolumeFrom());
            if (finalCountOrdersWithVolume.isEmpty() || !finalCountOrdersWithVolume.containsKey(key)) {
                for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                    sumRate += (Double) _map.getValue().getListRouteInfo().get(i).getRate();
                    sumTariff += (Double) _map.getValue().getListRouteInfo().get(i).getTariff();
                    sumCountDays = sumCountDays + _map.getValue().getListRouteInfo().get(i).getCountCircleDays();
                }
                List<Object> list = new ArrayList<>();
                list.add(sumRate);
                list.add(sumTariff);
                list.add(sumCountDays);
                finalCountOrdersWithVolume.put(key, list);
            } else {
                List<Object> list = finalCountOrdersWithVolume.get(key);
                for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                    list.set(0, (Double) list.get(0) + (Double) _map.getValue().getListRouteInfo().get(i).getRate());
                    list.set(1, (Double) list.get(1) + (Double) _map.getValue().getListRouteInfo().get(i).getTariff());
                    list.set(2, (Integer) list.get(2) + (Integer) _map.getValue().getListRouteInfo().get(i).getCountCircleDays());
                }
                finalCountOrdersWithVolume.replace(key, list);
            }
        }
        for (Map.Entry<Integer, List<Object>> _final : finalCountOrdersWithVolume.entrySet()) {
            List<Object> list = _final.getValue();
            Double yield = Math.round((((Double)list.get(0) - (Double)list.get(1)) / (Integer)list.get(2)) * 100) / 100.00d;
            _final.getValue().clear();
            _final.getValue().add(0, yield);
            finalCountOrdersWithVolume.replace(_final.getKey(), _final.getValue());
        }
        //yield = Math.round(((sumRate - sumTariff) / sumCountDays) * 100) / 100.00d;
        calculateCountOrders(map);
    }

    private void calculateCountOrders(Map<String, WagonFinalInfo> map) {
        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
            int tempCount = 0;
            int key = changeValueVolume(_map.getValue().getListRouteInfo().get(_map.getValue().getListRouteInfo().size() - 1).getRoute().getVolumePeriod().getVolumeFrom());
            for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                tempCount = tempCount + _map.getValue().getListRouteInfo().get(i).getCountCircleDays();
            }
            List<Object> list = finalCountOrdersWithVolume.get(key);
            if (list.size() == 1) {
                int count31Days = 0;
                int count40Days = 0;
                if (tempCount > JavaHelperBase.MAX_COUNT_DAYS) {
                    count31Days++;
                    count40Days += _map.getValue().getListRouteInfo().size();
                } else {
                    count31Days += _map.getValue().getListRouteInfo().size();
                    count40Days += _map.getValue().getListRouteInfo().size();
                }
                list.add(1, count31Days);
                list.add(2, count40Days);
                finalCountOrdersWithVolume.replace(key, list);
            } else {
                if (tempCount > JavaHelperBase.MAX_COUNT_DAYS) {
                    list.set(1, (Integer) list.get(1) + 1);
                    list.set(2, (Integer) list.get(2) + _map.getValue().getListRouteInfo().size());
                } else {
                    list.set(1, (Integer) list.get(1) + _map.getValue().getListRouteInfo().size());
                    list.set(2, (Integer) list.get(2) + _map.getValue().getListRouteInfo().size());
                }
                finalCountOrdersWithVolume.replace(key, list);
            }
        }
        calculateTotalCountOrders();
    }

    private void calculateTotalCountOrders() {
        Map<Integer, Route> mapOfRoutes = getListOfRoutes.getMapOfRoutes();
        for (Map.Entry<Integer, Route> _map : mapOfRoutes.entrySet()) {
            int count = 0;
            int key = changeValueVolume(_map.getValue().getVolumePeriod().getVolumeFrom());
            if (finalCountOrdersWithVolume.containsKey(key)) {
                List<Object> list = finalCountOrdersWithVolume.get(key);
                if (list.size() == 3) {
                    count = count + _map.getValue().getCountOrders();
                    list.add(3, count);
                } else {
                    list.set(3, (Integer) list.get(3) + _map.getValue().getCountOrders());
                }
                finalCountOrdersWithVolume.replace(key, list);
            }
        }
        logger.debug("finalCountOrdersWithVolume: {}", finalCountOrdersWithVolume);
    }

    private int changeValueVolume(int volume) {
        if (volume == 122) return 120;
        if (volume == 158) return 150;
        else return volume;
    }

    public Map<Integer, List<Object>> getFinalCountOrdersWithVolume() {
        return finalCountOrdersWithVolume;
    }

    public void setFinalCountOrdersWithVolume(Map<Integer, List<Object>> finalCountOrdersWithVolume) {
        this.finalCountOrdersWithVolume = finalCountOrdersWithVolume;
    }

    public Map<String, WagonFinalInfo> getNewMapWagonFinalInfo() {
        return newMapWagonFinalInfo;
    }

    public void setNewMapWagonFinalInfo(Map<String, WagonFinalInfo> newMapWagonFinalInfo) {
        this.newMapWagonFinalInfo = newMapWagonFinalInfo;
    }
}
