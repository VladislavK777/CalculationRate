package com.uraltranscom.calculaterate.model.station;

import lombok.AllArgsConstructor;
import lombok.Data;

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

@Data
@AllArgsConstructor
public class Country {
    private String idCountry; //Код страны
    private String nameCountry; //Название страны
}
