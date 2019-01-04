package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.model_ex.ExitModel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-01
 */

@Component
@Getter
public class CalculationRate extends GetObject {
    double yield = 0.00;
    double rate = 0.00;
    double sumCosts = 0.00;
    double sumCountDays = 0;
    ExitModel exitModel;

    public double getRate(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        if (volumeWagon == 114 || volumeWagon == 120 || volumeWagon == 122) {
            yield = 2000.00;
        } else if (volumeWagon == 138 || volumeWagon == 141) {
            yield = 2100.00;
        } else {
            yield = 2200.00;
        }
        commonLogicClass.startLogic(idStationDeparture, idStationDestination, idCargo, volumeWagon);
        exitModel = commonLogicClass.getExitModel();
        List<Route> list = exitModel.getExitList();

        for (Route route: list) {
            sumCountDays = sumCountDays + route.getCountDaysLoadUnload();
            sumCosts = sumCosts + route.getRate() + route.getTariff();
        }
        rate = (yield - (sumCosts/sumCountDays)) * sumCountDays;
        for(Route r: list) {
            System.out.println(r);
        }
        return rate;
    }
}
