package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 24.12.2018
 */

@Component
@Service
@Getter
public class BeginProcessingClass {
    private final Logger logger = LoggerFactory.getLogger(BeginProcessingClass.class);

    private BeginProcessingClass() {
    }

    public void startProcessor(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);

        List<String> listStationDepartureInfo = GetStationInfo.getStationInfo(idStationDeparture).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> listStationDestinationInfo = GetStationInfo.getStationInfo(idStationDestination).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> listDistance = GetDistanceBetweenStationsImpl.getDistanceBetweenStations(idStationDeparture, idStationDeparture, idCargo).stream().map(String::valueOf).collect(Collectors.toList());
        List<String> listCagroClass = GetTypeOfCargoImpl.getTypeOfCargo(idCargo);

        Route firstRoute = new Route(
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
                new Cargo(listCagroClass.get(0), listCagroClass.get(1), listCagroClass.get(2)),
                RouteType.FULL_ROUTE
        );
    }

}
