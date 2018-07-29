package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.additional_model.WagonRateAndTariff;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.dynamicdistributionpark.service.additional.PrepareDateForInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    ClassHandlerInsertRateOrTariffImpl classHandlerInsertRateOrTariff;

    private Map<String, WagonFinalInfo> newMapWagonFinalInfo = new HashMap<>();
    private double yield;

    public void updateMap(Map<String, WagonFinalInfo> map, String wagons, String rates, String tariffs) {
        List<WagonRateAndTariff> listRateAndTariff = PrepareDateForInsert.fillListForUpdate(wagons, rates, tariffs);
        Map<String, WagonFinalInfo> tempNewMap = map.entrySet()
                .stream().collect(Collectors.toMap(Map.Entry<String, WagonFinalInfo>::getKey, WagonFinalInfo::new));
        logger.debug("listRateAndTariff: {}", listRateAndTariff);
        for (Map.Entry<String, WagonFinalInfo> _map: tempNewMap.entrySet()) {
            int index = _map.getValue().getListRouteInfo().size() - 1;
            for (WagonRateAndTariff list : listRateAndTariff) {
                if (_map.getKey().equals(list.getNumberOfWagon())) {
                    if (_map.getValue().getListRouteInfo().get(index).getRate() != (Double) list.getRate()) {
                        _map.getValue().getListRouteInfo().get(index).setRate(list.getRate());
                    }
                    if (_map.getValue().getListRouteInfo().get(index).getTariff() != (Double) list.getTariff()) {
                        _map.getValue().getListRouteInfo().get(index).setTariff(list.getTariff());
                    }
                }
            }
        }
        newMapWagonFinalInfo.putAll(tempNewMap);
        logger.info("newMapWagonFinalInfo: {}, map: {}", newMapWagonFinalInfo, map);
        classHandlerInsertRateOrTariff.insertDate(newMapWagonFinalInfo, map);
        calculateYield(newMapWagonFinalInfo);
    }

    public void calculateYield(Map<String, WagonFinalInfo> map) {
        double sumRate = 0.00;
        double sumTariff = 0.00;
        int sumCountDays = 0;
        for (Map.Entry<String, WagonFinalInfo> _map: map.entrySet()) {
            int index = _map.getValue().getListRouteInfo().size() - 1;
            sumRate = sumRate + (Double) _map.getValue().getListRouteInfo().get(index).getRate();
            sumTariff = sumTariff + (Double) _map.getValue().getListRouteInfo().get(index).getTariff();
            sumCountDays = sumCountDays + _map.getValue().getListRouteInfo().get(index).getCountCircleDays();
        }
        yield = Math.round(((sumRate - sumTariff) / sumCountDays) * 100) / 100.00d;
        logger.debug("yield = {}", yield);
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
}
