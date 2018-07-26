package com.uraltranscom.dynamicdistributionpark.service.additional;

import com.uraltranscom.dynamicdistributionpark.controller.BasicController;
import com.uraltranscom.dynamicdistributionpark.model.additional_model.WagonRateAndTariff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Дополнительный класс для подготовки ставки и тарифа для обновления
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 *
 */

public class PrepareDateForInsert {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(PrepareDateForInsert.class);

    public static List<WagonRateAndTariff> fillListForUpdate(String wagons, String rates, String tariffs) {
        List<WagonRateAndTariff> listRateAndTariff = new ArrayList<>();
        String [] rateArray = rates.split(",");
        String [] tariffArray = tariffs.split(",");
        String [] wagonArray = wagons.split(",");
        for (int i = 0; i < wagonArray.length; i++) {
            listRateAndTariff.add(new WagonRateAndTariff(wagonArray[i], Double.valueOf(rateArray[i]), Double.valueOf(tariffArray[i])));
        }
        return listRateAndTariff;
    }
}
