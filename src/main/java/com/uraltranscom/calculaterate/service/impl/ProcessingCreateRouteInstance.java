package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Cargo;
import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.model.RouteType;
import com.uraltranscom.calculaterate.model.Station;
import com.uraltranscom.calculaterate.util.PrepareDistanceOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
public class ProcessingCreateRouteInstance {
    private final Logger logger = LoggerFactory.getLogger(ProcessingCreateRouteInstance.class);

    private ProcessingCreateRouteInstance() {
    }

    public Route getRouteInstance(Station stationDeparture, Station stationDestination, String distance, int volumeWagon, Cargo cargo, RouteType routeType) {
        logger.info("Start process create Route");

        Route route = new Route(
                stationDeparture,
                stationDestination,
                distance,
                volumeWagon,
                cargo,
                routeType,
                (int) Math.ceil(Integer.parseInt(distance) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distance)))
        );

        logger.info("Route: {}", route.toString());
        return route;
    }
}
