package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.service.GetFullMonthCircleOfWagon;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import com.uraltranscom.calculaterate.service.additional.PrepareDistanceOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@Component
public class GetFullMonthCircleOfWagonImpl extends JavaHelperBase implements GetFullMonthCircleOfWagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetFullMonthCircleOfWagonImpl.class);

    private Map<String, List<Integer>> mapOfDaysOfWagon = new HashMap<>();

    private GetFullMonthCircleOfWagonImpl() {
    }

    @Override
    public int fullDays(String numberOfWagon, Integer distanceOfEmpty, String distanceOfRoute) {
        List<Integer> list = new ArrayList<>();
        if (getNumberOfDaysOfWagon(numberOfWagon) == 0) {
            int fullMonthCircle = 0;

            // Прибавляем количество дней порожнего расстояния до станции отпраления следующего рейса
            fullMonthCircle += Math.ceil(distanceOfEmpty / PrepareDistanceOfDay.getDistanceOfDay(distanceOfEmpty));
            // Погрузка
            fullMonthCircle += LOADING_WAGON;
            // Расчитываем количество дней следующего рейса
            fullMonthCircle += Math.ceil(Integer.parseInt(distanceOfRoute) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceOfRoute)));
            // Прибавляем количество дней выгрузки
            fullMonthCircle += UNLOADING_WAGON;

            list.add(fullMonthCircle);
            mapOfDaysOfWagon.put(numberOfWagon, list);

            return fullMonthCircle;
        } else {
            int sumCountDays = getNumberOfDaysOfWagon(numberOfWagon);
            int fullMonthCircle = 0;

            // Прибавляем количество дней порожнего расстояния до станции отпраления следующего рейса
            fullMonthCircle += Math.ceil(distanceOfEmpty / PrepareDistanceOfDay.getDistanceOfDay(distanceOfEmpty));
            // Погрузка
            fullMonthCircle += LOADING_WAGON;
            // Расчитываем количество дней следующего рейса
            fullMonthCircle += Math.ceil(Integer.parseInt(distanceOfRoute) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceOfRoute)));
            // Прибавляем количество дней вызгрузки
            fullMonthCircle += UNLOADING_WAGON;

            sumCountDays += fullMonthCircle;
            list = mapOfDaysOfWagon.get(numberOfWagon);
            list.add(fullMonthCircle);
            mapOfDaysOfWagon.replace(numberOfWagon, list);

            return sumCountDays;
        }
    }

    private Integer getNumberOfDaysOfWagon(String numberOfWagon) {
        int number = 0;
        if (!mapOfDaysOfWagon.containsKey(numberOfWagon)) {
            return 0;
        } else {
            List<Integer> list = mapOfDaysOfWagon.get(numberOfWagon);
            for(Integer _list: list) {
                number += _list;
            }
            return number;
        }
    }

    public List<Integer> getListOfDaysOfWagon(String numberOfWagon) {
        return mapOfDaysOfWagon.getOrDefault(numberOfWagon, null);
    }

    public Map<String, List<Integer>> getMapOfDaysOfWagon() {
        return mapOfDaysOfWagon;
    }

    public void setMapOfDaysOfWagon(Map<String, List<Integer>> mapOfDaysOfWagon) {
        this.mapOfDaysOfWagon = mapOfDaysOfWagon;
    }
}
