package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * Класс Страна
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
@ToString
public class Country {
    private String idCountry; //Код страны
    private String nameCountry; //Название страны
}
