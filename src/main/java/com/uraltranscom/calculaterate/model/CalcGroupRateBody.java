package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate
 * @date 13.03.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcGroupRateBody {
    private File file;
}
