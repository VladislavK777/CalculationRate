package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 26.12.2018
 */

@Getter
@Setter
@AllArgsConstructor
public class Distance {
    private String distance; //Полная дистанция
    private String distanceStart; //Дистанция до границы от станции Отправления
    private String distanceEnd; //Дистанция от границы от станции Назначения
    private String routeCountries; //Карта дистанций по кодам стран
    private String routeDistance; //Карта дистанций между границами
}
