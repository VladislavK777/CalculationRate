package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.model_ex.ExitModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-01
 */

@Component
public class CalculationRate extends GetObject {
    double yield = 2100.00;
    double rate = 0.00;

    public double getRate(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        double sumCosts = 0.00;
        double sumCountDays = 0;
        commonLogicClass.startLogic(idStationDeparture, idStationDestination, idCargo, volumeWagon);
        ExitModel exitModel = commonLogicClass.getExitModel();
        List<Route> list = exitModel.getExitList();

        for (Route route: list) {
            sumCountDays = sumCountDays + route.getCountDaysLoadUnload();
            sumCosts = sumCosts + route.getRate() + route.getTariff();
        }
        rate = (yield - (sumCosts/sumCountDays)) * sumCountDays;
        System.out.println(list);
        return rate;
    }
}
