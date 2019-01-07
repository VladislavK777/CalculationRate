package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.model_ex.ExitModel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
    int sumCountDays = 0;
    int sumFullCountDays = 0;
    int sumDistance = 0;
    ExitModel exitModel;

    public void getRate(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
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
            sumFullCountDays = sumFullCountDays + route.getFullCountDays();
            sumCosts = sumCosts + route.getRate() + route.getTariff();
            sumCountDays = sumCountDays + route.getCountDays();
            sumDistance = sumDistance + Integer.parseInt(route.getDistance());
        }
        rate = Math.round(((yield - (sumCosts / sumFullCountDays)) * sumFullCountDays) * 100) / 100.00d;
        exitModel = reCalcValues();
    }

    private ExitModel reCalcValues() {
        sumCosts = 0.00;
        exitModel.getExitList().stream()
                .filter(Route::isFlagNeedCalc)
                .peek(route -> route.setRate(rate))
                .collect(Collectors.toList());
        for (Route route: exitModel.getExitList()) {
            sumCosts =  Math.round((sumCosts + route.getRate() + route.getTariff()) * 100) / 100.00d;
            yield = Math.round((sumCosts / sumFullCountDays) * 100) / 100.00d;
        }
        return exitModel;
    }
}