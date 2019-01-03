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
    private int volumeWagon; //Объем вагона
    private Cargo cargo; //Груз
    private RouteType routeType; //Тип рейса
    private int countDays; //Количество дней
    private int countDaysLoadUnload; //Количество дней погрузки/выгрузки
    private double rate; //Ставка
    private double tariff; //Тариф

    public Route(Station stationDeparture, Station stationDestination, String distance, int volumeWagon, Cargo cargo, RouteType routeType, int countDays) {
        this.stationDeparture = stationDeparture;
        this.stationDestination = stationDestination;
        this.distance = distance;
        this.volumeWagon = volumeWagon;
        this.cargo = cargo;
        this.routeType = routeType;
        this.countDays = countDays;
    }
}