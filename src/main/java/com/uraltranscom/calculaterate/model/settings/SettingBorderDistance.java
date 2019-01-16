package com.uraltranscom.calculaterate.model.settings;

import lombok.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SettingBorderDistance {
    private int id;
    private int distanceFrom;
    private int distanceTo;
    private int coefficient;
}
