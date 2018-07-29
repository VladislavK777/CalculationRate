package com.uraltranscom.dynamicdistributionpark.service;

/**
 *
 * Интерфейс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public interface GetFullMonthCircleOfWagon {
    int fullDays(String numberOfWagon, Integer distanceOfEmpty, String distanceOfRoute);
}

