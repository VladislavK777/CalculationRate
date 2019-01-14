package com.uraltranscom.calculaterate.service.impl;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-01
 */

/*
@Component
@Getter
public class CalculationRate {
    double yield = 0.00;
    double rate = 0.00;
    double sumCosts = 0.00;
    int sumCountDays = 0;
    int sumFullCountDays = 0;
    int sumDistance = 0;
    TotalModel totalModel;

    @Autowired
    private CommonLogicClass commonLogicClass;

    public void getRate(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        if (getVolumeGroup(volumeWagon) == 120) {
            yield = 2000.00;
        } else if (getVolumeGroup(volumeWagon) == 138) {
            yield = 2100.00;
        } else {
            yield = 2200.00;
        }
        commonLogicClass.startLogic(idStationDeparture, idStationDestination, idCargo, volumeWagon);
        totalModel = commonLogicClass.getTotalModel();
        List<Route> list = totalModel.getExitList();

        for (Route route: list) {
            sumFullCountDays = sumFullCountDays + route.getFullCountDays();
            sumCosts = sumCosts + route.getRate() + route.getTariff();
            sumCountDays = sumCountDays + route.getCountDays();
            sumDistance = sumDistance + Integer.parseInt(route.getDistance());
        }
        rate = Math.round(((yield - (sumCosts / sumFullCountDays)) * sumFullCountDays) * 100) / 100.00d;
        totalModel = reCalcValues();
        check2load();
    }

    private TotalModel reCalcValues() {
        sumCosts = 0.00;
        totalModel.getExitList().stream()
                .filter(Route::isFlagNeedCalc)
                .peek(route -> route.setRate(rate))
                .collect(Collectors.toList());
        for (Route route: totalModel.getExitList()) {
            sumCosts =  Math.round((sumCosts + route.getRate() + route.getTariff()) * 100) / 100.00d;
            yield = Math.round((sumCosts / sumFullCountDays) * 100) / 100.00d;
        }
        return totalModel;
    }

    private void check2load() {
        boolean flag = false;
        for (Route route: totalModel.getExitList()) {
            if (route.getCountDaysLoadAndUnload() == JavaHelperBase.LOADING_2_WAGON && !flag) {
                flag = true;
            } else if (route.getCountDaysLoadAndUnload() == JavaHelperBase.LOADING_2_WAGON && flag) {
                route.setNewCountDaysLoadAndUnload(JavaHelperBase.LOADING_WAGON);
                flag = false;
            }
        }
    }
}
*/
