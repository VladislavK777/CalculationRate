package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.dao.*;
import com.uraltranscom.calculaterate.model.Station;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-01
 */

public abstract class GetObject {
    @Autowired
    ProcessingCreateRouteInstance processingCreateRouteInstance;
    @Autowired
    GetStationInfoDAO getStationInfoDAO;
    @Autowired
    GetTypeOfCargoDAO getTypeOfCargo;
    @Autowired
    GetDistanceBetweenStationsDAO getDistanceBetweenStations;
    @Autowired
    GetTariffDAO getTariffDAO;
    @Autowired
    GetReturnStationDAO getReturnStationDAO;
    @Autowired
    GetTariff getTariff;
    @Autowired
    ExceptionReturnRoute exceptionReturnRoute;
    @Autowired
    protected CalculationRate calculationRate;
    @Autowired
    CommonLogicClass commonLogicClass;
}
