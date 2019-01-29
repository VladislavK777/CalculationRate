package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 29.01.2019
 */

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RatesList {
    private int volume;
    private Station stationFrom;
    private Road roadTo;
    private double actualRate;
}
