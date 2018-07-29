package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * Класс для инсерта в бд ставки и тарифа
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
public class ClassHandlerInsertRateOrTariffImpl {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerInsertRateOrTariffImpl.class);

    @Autowired
    private InsertRateImpl insertRate;
    @Autowired
    private InsertTariffImpl insertTariff;

    void insertDate(Map<String, WagonFinalInfo> newMap, Map<String, WagonFinalInfo> map) {
        for (Map.Entry<String, WagonFinalInfo> _map: map.entrySet()) {
            int index = _map.getValue().getListRouteInfo().size() - 1;
            for (Map.Entry<String, WagonFinalInfo> _newMap : newMap.entrySet()) {
                if (_map.getKey().equals(_newMap.getKey())) {
                    if (!_map.getValue().equals(_newMap.getValue())) {
                        // Вставляем ставку в БД
                        insertRate.insertRateOfTariff(_newMap.getValue().getListRouteInfo().get(index).getRoute().getKeyOfStationDeparture(), _newMap.getValue().getListRouteInfo().get(index).getRoute().getKeyOfStationDestination(), _newMap.getValue().getListRouteInfo().get(index).getRoute().getCargo().getCargoType(), (Double) _newMap.getValue().getListRouteInfo().get(index).getRate());
                        // Вставляем тариф в БД
                        insertTariff.insertRateOfTariff(_newMap.getValue().getListRouteInfo().get(index).getCurrentKeyOfStationOfWagon(), _newMap.getValue().getListRouteInfo().get(index).getRoute().getKeyOfStationDeparture(), _newMap.getValue().getListRouteInfo().get(index).getCargoType(), (Double) _newMap.getValue().getListRouteInfo().get(index).getTariff());
                    }
                }
            }
        }
    }
}
