package com.uraltranscom.calculaterate.service.impl;

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
 * @version 2.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 * 13.10.2018
 *   1. Версия 2.0
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
            for (Map.Entry<String, WagonFinalInfo> _newMap : newMap.entrySet()) {
                if (_map.getKey().equals(_newMap.getKey())) {
                    for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                        //if (!_map.getValue().equals(_newMap.getValue())) {
                            // Вставляем ставку в БД
                            if (!_map.getValue().getListRouteInfo().get(_map.getValue().getListRouteInfo().size() - 1).getRoute().getNameOfStationDestination().equals("")) {
                                insertRate.insertRate(_newMap.getValue().getListRouteInfo().get(i).getRoute().getKeyOfStationDeparture(),
                                        _newMap.getValue().getListRouteInfo().get(i).getRoute().getKeyOfStationDestination(),
                                        _newMap.getValue().getListRouteInfo().get(i).getRoute().getCargo().getCargoType(),
                                        (Double) _newMap.getValue().getListRouteInfo().get(i).getRate());
                            }
                            /**
                            // Вставляем тариф в БД
                            insertTariff.insertTariff(_newMap.getValue().getListRouteInfo().get(i).getCurrentKeyOfStationOfWagon(),
                                    _newMap.getValue().getListRouteInfo().get(i).getRoute().getKeyOfStationDeparture(),
                                    _newMap.getValue().getListRouteInfo().get(i).getCargo().getKeyCargo(),
                                    (Double) _newMap.getValue().getListRouteInfo().get(i).getTariff());
                             */
                        //}
                    }
                }
            }
        }
    }
}
