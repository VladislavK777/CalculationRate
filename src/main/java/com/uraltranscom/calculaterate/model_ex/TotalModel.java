package com.uraltranscom.calculaterate.model_ex;

import com.uraltranscom.calculaterate.model.route.Route;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 25.12.2018
 */

@Data
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
    private Object actualYield;

    public TotalModel(List<Route> totalList, int idGroup, int sumDistance, int sumCountDays, int sumCountDaysLoadUnload, int sumFullCountDays, double sumRateOrTariff, double yield) {
        this.totalList = totalList;
        this.idGroup = idGroup;
        this.sumDistance = sumDistance;
        this.sumCountDays = sumCountDays;
        this.sumCountDaysLoadUnload = sumCountDaysLoadUnload;
        this.sumFullCountDays = sumFullCountDays;
        this.sumRateOrTariff = Math.round(sumRateOrTariff * 100) / 100.00d;
        this.yield = Math.round(yield * 100) / 100.00d;
    }
}
