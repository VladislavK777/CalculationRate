package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.*;
import com.uraltranscom.calculaterate.service.additional.PrepareDistanceOfDay;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Класс Получения Роута
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 24.12.2018
 *
 * 24.12.2018
 *   1. Версия 1.0
 *
 */

@Component
public class BeginProcessingGetRouteInstance {
    private final Logger logger = LoggerFactory.getLogger(BeginProcessingGetRouteInstance.class);

    private BeginProcessingGetRouteInstance() {
    }

    public Route getRouteInstance(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);

        List<String> listStationDepartureInfo = GetStationInfo.getStationInfo(idStationDeparture).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> listStationDestinationInfo = GetStationInfo.getStationInfo(idStationDestination).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> listDistance = GetDistanceBetweenStationsImpl.getDistanceBetweenStations(idStationDeparture, idStationDeparture, idCargo).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> listCargoClass = GetTypeOfCargoImpl.getTypeOfCargo(idCargo);

        Route route = new Route(
                new Station(listStationDepartureInfo.get(0), listStationDepartureInfo.get(1),
                        new RoadStation(listStationDepartureInfo.get(2), listStationDepartureInfo.get(3), listStationDepartureInfo.get(4),
                                new Country(
                                        listStationDepartureInfo.get(5),
                                        listStationDepartureInfo.get(6)
                                )
                        )
                ),
                new Station(listStationDestinationInfo.get(0), listStationDestinationInfo.get(1),
                        new RoadStation(listStationDestinationInfo.get(2), listStationDestinationInfo.get(3), listStationDestinationInfo.get(4),
                                new Country(
                                        listStationDestinationInfo.get(5),
                                        listStationDestinationInfo.get(6)
                                )
                        )
                ),
                listDistance.get(0),
                volumeWagon,
                new Cargo(listCargoClass.get(0), listCargoClass.get(1), listCargoClass.get(2)),
                RouteType.FULL_ROUTE,
                (int) Math.ceil(Integer.parseInt(listDistance.get(0)) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(listDistance.get(0))))
        );
        logger.info("Route: {}", route.toString());
        return route;
    }
}
