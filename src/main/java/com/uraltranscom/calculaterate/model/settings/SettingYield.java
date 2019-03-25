package com.uraltranscom.calculaterate.model.settings;

import lombok.*;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 16.01.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingYield {
    private int id;
    private int volumeGroup;
    private double yield;
}
