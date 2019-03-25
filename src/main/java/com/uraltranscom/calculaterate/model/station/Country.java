package com.uraltranscom.calculaterate.model.station;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 24.12.2018
 */

@Data
@AllArgsConstructor
public class Country {
    private String idCountry; //Код страны
    private String nameCountry; //Название страны
}
