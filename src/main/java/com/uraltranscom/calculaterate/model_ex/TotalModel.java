package com.uraltranscom.calculaterate.model_ex;

import com.uraltranscom.calculaterate.model.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 25.12.2018
 */

@Getter
@AllArgsConstructor
@ToString
public class TotalModel {
    private List<Route> totalList;
    private int idGroup;
    private int sumDistance;
    private int sumCountDays;
    private int sumCountDaysLoadUnload;
    private int sumFullCountDays;
    private double sumRateOrTariff;
    private double yield;
}
