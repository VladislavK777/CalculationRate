package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 15.01.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpBodyRate {
    private String stationFrom;
    private String stationTo;
    private String cargo;
    private int volume;
}
