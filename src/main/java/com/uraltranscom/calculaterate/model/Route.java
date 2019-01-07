package com.uraltranscom.calculaterate.model;

import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@EqualsAndHashCode
@ToString
public class Route {
    private Station stationDeparture; //Станция отправления
    private Station stationDestination; //Станция назвачения
    private String distance; //Расстояние маршрута
    private Volume volumeWagon; //Объем вагона
    private Cargo cargo; //Груз
    private RouteType routeType; //Тип рейса
    private int countDays; //Количество дней
    private int countDaysLoadAndUnload; //Количество дней погрузки/выгрузки
    private int fullCountDays; //Полное количество дней
    private double rate; //Ставка
    private double tariff; //Тариф
    private boolean flagNeedCalc; //Флаг, ставку который расчитываем (true - да)

    public Route(Station stationDeparture, Station stationDestination, String distance, int volumeWagon, Cargo cargo, RouteType routeType, int countDays, int countDaysLoadAndUnload, boolean flagNeedCalc) {
        this.stationDeparture = stationDeparture;
        this.stationDestination = stationDestination;
        this.distance = distance;
        this.volumeWagon = new Volume(volumeWagon);
        this.cargo = cargo;
        this.routeType = routeType;
        this.countDays = countDays;
        this.countDaysLoadAndUnload = countDaysLoadAndUnload;
        this.fullCountDays = countDays + countDaysLoadAndUnload;
        this.flagNeedCalc = flagNeedCalc;
    }

    public void setNewCountDays(int countDays) {
        this.countDays = countDays;
        this.fullCountDays = countDays + this.countDaysLoadAndUnload;
    }

    public void setNewCountDaysLoadAndUnload(int newCountDaysLoadAndUnload) {
        setCountDaysLoadAndUnload(newCountDaysLoadAndUnload);
        this.fullCountDays = this.countDays + newCountDaysLoadAndUnload;
    }
}