package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.service.GetFullMonthCircleOfWagon;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import com.uraltranscom.dynamicdistributionpark.service.additional.PrepareDistanceOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Класс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 * Implementation for {@link GetFullMonthCircleOfWagon} interface
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

@Service
public class GetFullMonthCircleOfWagonImpl extends JavaHelperBase implements GetFullMonthCircleOfWagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetFullMonthCircleOfWagonImpl.class);

    private Map<String, Integer> mapOfDaysOfWagon = new HashMap<>();

    private GetFullMonthCircleOfWagonImpl() {
    }

    @Override
    public int fullDays(String numberOfWagon, Integer distanceOfEmpty, String distanceOfRoute) {
        logger.info("ddd: {}", mapOfDaysOfWagon);
        if (getNumberOfDaysOfWagon(numberOfWagon) == 0) {
            int fullMonthCircle = 0;

            // Прибавляем количество дней порожнего расстояния до станции отпраления следующего рейса
            fullMonthCircle += Math.ceil(distanceOfEmpty / PrepareDistanceOfDay.getDistanceOfDay(distanceOfEmpty));
            // Погрузка
            fullMonthCircle += LOADING_WAGON;
            // Расчитываем количество дней следующего рейса
            fullMonthCircle += Math.ceil(Integer.parseInt(distanceOfRoute) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceOfRoute)));
            // Прибавляем количество дней разгрузки
            fullMonthCircle += UNLOADING_WAGON;

            mapOfDaysOfWagon.put(numberOfWagon, fullMonthCircle);

            return fullMonthCircle;
        } else {
            int fullMonthCircle = getNumberOfDaysOfWagon(numberOfWagon);

            // Прибавляем количество дней порожнего расстояния до станции отпраления следующего рейса
            fullMonthCircle += Math.ceil(distanceOfEmpty / PrepareDistanceOfDay.getDistanceOfDay(distanceOfEmpty));
            // Погрузка
            fullMonthCircle += LOADING_WAGON;
            // Расчитываем количество дней следующего рейса
            fullMonthCircle += Math.ceil(Integer.parseInt(distanceOfRoute) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceOfRoute)));
            // Прибавляем количество дней разгрузки
            fullMonthCircle += UNLOADING_WAGON;

            mapOfDaysOfWagon.replace(numberOfWagon, fullMonthCircle);
            return fullMonthCircle;
        }
    }

    private int getNumberOfDaysOfWagon(String numberOfWagon) {
        return mapOfDaysOfWagon.getOrDefault(numberOfWagon, 0);
    }
}
