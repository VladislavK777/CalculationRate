package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 26.12.2018
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Distance {
    private String distanceStart; //Дистанция до границы от станции Отправления
    private String distanceEnd; //Дистанция от границы от станции Назначения
    private String distance; //Полная дистанция
    private String routeCountries; //Карта дистанций по кодам стран
    private String routeDistance; //Карта дистанций между границами
}
