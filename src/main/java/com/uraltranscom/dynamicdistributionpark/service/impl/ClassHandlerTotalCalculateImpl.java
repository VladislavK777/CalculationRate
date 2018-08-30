package com.uraltranscom.dynamicdistributionpark.service.impl;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ClassHandlerTotalCalculateImpl extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerTotalCalculateImpl.class);

    @Autowired
    private ClassHandlerInsertRateOrTariffImpl classHandlerInsertRateOrTariff;

    private Map<String, WagonFinalInfo> newMapWagonFinalInfo = new HashMap<>();
    private double yield;
    private int count31Days;
    private int count40Days;

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
        yield = 0;
        double sumRate = 0.00;
        double sumTariff = 0.00;
        int sumCountDays = 0;
        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
            for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                sumRate += (Double) _map.getValue().getListRouteInfo().get(i).getRate();
                sumTariff += (Double) _map.getValue().getListRouteInfo().get(i).getTariff();
                sumCountDays = sumCountDays + _map.getValue().getListRouteInfo().get(i).getCountCircleDays();
            }
        }
        yield = Math.round(((sumRate - sumTariff) / sumCountDays) * 100) / 100.00d;
        calculateCountOrders(map);
        logger.debug("yield: {}", yield);
    }

    private void calculateCountOrders(Map<String, WagonFinalInfo> map) {
        count31Days = 0;
        count40Days = 0;
        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
            int tempCount = 0;
            for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                tempCount = tempCount + _map.getValue().getListRouteInfo().get(i).getCountCircleDays();
            }
            if (tempCount > 31) {
                count40Days += _map.getValue().getListRouteInfo().size();
                count31Days++;
            } else {
                count40Days += _map.getValue().getListRouteInfo().size();
                count31Days += _map.getValue().getListRouteInfo().size();
            }
        }
        logger.debug("count31Days: {}, count40Days: {}", count31Days, count40Days);
    }


    public Map<String, WagonFinalInfo> getNewMapWagonFinalInfo() {
        return newMapWagonFinalInfo;
    }

    public void setNewMapWagonFinalInfo(Map<String, WagonFinalInfo> newMapWagonFinalInfo) {
        this.newMapWagonFinalInfo = newMapWagonFinalInfo;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public int getCount31Days() {
        return count31Days;
    }

    public void setCount31Days(int count31Days) {
        this.count31Days = count31Days;
    }

    public int getCount40Days() {
        return count40Days;
    }

    public void setCount40Days(int count40Days) {
        this.count40Days = count40Days;
    }
}
