package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.model_ex.ExitModel;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import java.util.List;
import java.util.stream.Collectors;

import static com.uraltranscom.calculaterate.util.GetVolumeGroup.getVolumeGroup;

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
        if (getVolumeGroup(volumeWagon) == 120) {
            yield = 2000.00;
        } else if (getVolumeGroup(volumeWagon) == 138) {
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
        check2load();
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

    private void check2load() {
        boolean flag = false;
        for (Route route: exitModel.getExitList()) {
            if (route.getCountDaysLoadAndUnload() == JavaHelperBase.LOADING_2_WAGON && !flag) {
                flag = true;
            } else if (route.getCountDaysLoadAndUnload() == JavaHelperBase.LOADING_2_WAGON && flag) {
                route.setNewCountDaysLoadAndUnload(JavaHelperBase.LOADING_WAGON);
                flag = false;
            }
        }
    }
}
