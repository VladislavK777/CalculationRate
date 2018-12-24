package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * Класс Станция
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
@EqualsAndHashCode
@AllArgsConstructor
public class Station {
    private String idStation; //Код станции
    private String nameStation; //Название станция
    private RoadStation roadStation; //Дорога станции
}
