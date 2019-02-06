package com.uraltranscom.calculaterate.model.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 06.02.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingOther {
    private int id;
    private String name;
    private String volume;
    private double value;
}
