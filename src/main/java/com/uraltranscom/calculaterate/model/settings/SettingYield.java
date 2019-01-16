package com.uraltranscom.calculaterate.model.settings;

import lombok.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SettingYield {
    private int id;
    private int volumeGroup;
    private double yield;
}
