package com.uraltranscom.calculaterate.model;

import lombok.Data;

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

@Data
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

    public Route(Station stationDeparture, Station stationDestination, int distance, Cargo cargo, int countDays, int countDaysLoadAndUnload, int fullCountDays, double rate, double tariff, boolean flagNeedCalc) {
        this.stationDeparture = stationDeparture;
        this.stationDestination = stationDestination;
        this.distance = distance;
        this.cargo = cargo;
        this.countDays = countDays;
        this.countDaysLoadAndUnload = countDaysLoadAndUnload;
        this.fullCountDays = fullCountDays;
        this.rate = Math.round(rate * 100) / 100.00d;
        this.tariff = Math.round(tariff * 100) / 100.00d;
        this.flagNeedCalc = flagNeedCalc;
    }
}