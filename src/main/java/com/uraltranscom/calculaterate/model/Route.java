package com.uraltranscom.calculaterate.model;

import lombok.*;

/**
 *
 * Класс Маршрута
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 24.12.2018
 *
 * 24.12.2018
 *   1. Версия 1.0
 *
 */

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Route {
    private Station stationDeparture; //Станция отправления
    private Station stationDestination; //Станция назвачения
    private int distance; //Расстояние маршрута
    private Cargo cargo; //Груз
    private int countDays; //Количество дней
    private int countDaysLoadAndUnload; //Количество дней погрузки/выгрузки
    private int fullCountDays; //Полное количество дней
    private double rate; //Ставка
    private double tariff; //Тариф
    private boolean flagNeedCalc; //Флаг, ставку который расчитываем (true - да)
}