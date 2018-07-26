package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.additional_model.WagonRateAndTariff;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.dynamicdistributionpark.service.additional.PrepareDateForInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class ClassHandlerInsertRateOrTariffImpl {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerInsertRateOrTariffImpl.class);

    @Autowired
    InsertRateImpl insertRate;
    @Autowired
    InsertTariffImpl insertTariff;

    public void insertDate(Map<String, WagonFinalInfo> map, String wagons, String rates, String tariffs) {
        List<WagonRateAndTariff> listRateAndTariff = PrepareDateForInsert.fillListForUpdate(wagons, rates, tariffs);
        for (Map.Entry<String, WagonFinalInfo> _map: map.entrySet()) {
            for (WagonRateAndTariff list : listRateAndTariff) {
                if (_map.getKey().equals(list.getNumberOfWagon())) {
                    // Вставляем ставку в БД
                    insertRate.insertRateOfTariff(_map.getValue().getRoute().getKeyOfStationDeparture(), _map.getValue().getRoute().getKeyOfStationDestination(), _map.getValue().getRoute().getCargo().getCargoType(), list.getRate());
                    // Вставляем тариф в БД
                    insertTariff.insertRateOfTariff(_map.getValue().getCurrentKeyOfStationOfWagon(), _map.getValue().getRoute().getKeyOfStationDeparture(), _map.getValue().getCargoType(), list.getTariff());
                }
            }
        }
    }
}
