package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * Класс Дорога
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
public class RoadStation {
    private String idRoad; //Идентификатор дороги
    private String nameRoad; //Название дороги
    private String fullNameRoad; //Полное название дороги
    private Country countryRoad; //Код страны дороги
}
