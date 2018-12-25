package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.*;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 25.12.2018
 */


public class CommonLogicClass {
    private final Logger logger = LoggerFactory.getLogger(CommonLogicClass.class);

    @Autowired
    ProcessingCreateRouteInstance processingCreateRouteInstance;

    void startLogic(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);

        Station stationDeparture = GetStationInfo.getObject(idStationDeparture);
        Station stationDestination = GetStationInfo.getObject(idStationDestination);
        List<String> listDistance = GetDistanceBetweenStationsImpl.getDistanceBetweenStations(idStationDeparture, idStationDestination, idCargo).stream().map(String::valueOf).collect(Collectors.toList());
        Cargo cargo = GetTypeOfCargoImpl.getObject(idCargo);

        if (JavaHelperBase.LIST_ROADS_WITHOUT_CHECK_DIST.contains(stationDeparture.getRoadStation().getNameRoad())) {
            Route firstRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, listDistance.get(0), volumeWagon, cargo, RouteType.FULL_ROUTE);
        } else if (JavaHelperBase.LIST_ROADS_PRIBALT.contains(stationDeparture.getRoadStation().getNameRoad())) {
            // TODO переделать
            Station stationDeparture1 = GetStationInfo.getObject("273501");
            Station stationDestination1 = GetStationInfo.getObject("037202");
            Cargo cargo1 = GetTypeOfCargoImpl.getObject("094076");
            String distance = "1236";
            Route firstRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture1, stationDestination1, distance, volumeWagon, cargo1, RouteType.FULL_ROUTE);
            firstRoute.setCountDays(6);
            firstRoute.setCountDaysLoadUnload(13);
            firstRoute.setRate(52000);
            Station stationDeparture2 = GetStationInfo.getObject("037202");
            Station stationDestination2 = stationDeparture;



        }
    }
}
