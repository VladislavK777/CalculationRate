package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.*;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.uraltranscom.calculaterate.util.PrepareMapParams.prepareMapWithParams;

/**
 *
 * Класс основной логике
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.12.2018
 *
 * 26.12.2018
 *   1. Версия 1.0
 *
 */

@Component
public class CommonLogicClass {
    private final Logger logger = LoggerFactory.getLogger(CommonLogicClass.class);

    @Autowired
    ProcessingCreateRouteInstance processingCreateRouteInstance;
    @Autowired
    GetStationInfo getStationInfo;
    @Autowired
    GetTypeOfCargoImpl getTypeOfCargo;
    @Autowired
    GetDistanceBetweenStationsImpl getDistanceBetweenStations;

    void startLogic(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);

        Station stationDeparture = getStationInfo.getObject(prepareMapWithParams(idStationDeparture));
        Station stationDestination = getStationInfo.getObject(prepareMapWithParams(idStationDestination));
        Distance distance = getDistanceBetweenStations.getObject(prepareMapWithParams(idStationDeparture, idStationDestination, idCargo));
        Cargo cargo = getTypeOfCargo.getObject(prepareMapWithParams(idCargo));

        if (JavaHelperBase.LIST_ROADS_WITHOUT_CHECK_DIST.contains(stationDeparture.getRoadStation().getNameRoad())) {
            Route firstRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE);
        } else if (JavaHelperBase.LIST_ROADS_PRIBALT.contains(stationDeparture.getRoadStation().getNameRoad())) {
            // TODO переделать
            Station stationDeparture1 = getStationInfo.getObject(prepareMapWithParams("273501"));
            Station stationDestination1 =getStationInfo.getObject(prepareMapWithParams("037202"));
            Cargo cargo1 = getTypeOfCargo.getObject(prepareMapWithParams("094076"));
            Distance distance1 = new Distance("1236", "", "", "", "");

            Route firstRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture1, stationDestination1, distance1.getDistance(), volumeWagon, cargo1, RouteType.FULL_ROUTE);
            firstRoute.setCountDays(6);
            firstRoute.setCountDaysLoadUnload(13);
            firstRoute.setRate(52000);

            Station stationDeparture2 = stationDestination1;
            Station stationDestination2 = stationDeparture;



        }
    }
}
