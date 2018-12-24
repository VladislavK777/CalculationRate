package com.uraltranscom.calculaterate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
public class Route {
    private Station stationDeparture; //Станция отправления
    private Station stationDestination; //Станция назвачения
    private String distance; //Расстояние маршрута
    private int volumeWagon; //Объем вагона
    private Cargo cargo; //Груз
    private RouteType routeType; //Тип рейса
    private int countDays; //Количество дней

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