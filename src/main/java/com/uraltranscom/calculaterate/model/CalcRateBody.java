package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 15.01.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcRateBody {
    private String stationFrom;
    private String stationTo;
    private String cargo;
    private int volume;
    private File file;
}
