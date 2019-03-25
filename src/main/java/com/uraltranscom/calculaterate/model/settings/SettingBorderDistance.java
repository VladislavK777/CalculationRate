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
public class SettingBorderDistance {
    private int id;
    private int distanceFrom;
    private int distanceTo;
    private int coefficient;
}
