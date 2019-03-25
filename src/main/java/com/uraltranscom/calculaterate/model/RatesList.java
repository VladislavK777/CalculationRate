package com.uraltranscom.calculaterate.model;

import com.uraltranscom.calculaterate.model.station.Station;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 29.01.2019
 */

@Data
@AllArgsConstructor
public class RatesList {
    private int volume;
    private Station stationFrom;
    private Station stationTo;
    private double actualRate;
}
