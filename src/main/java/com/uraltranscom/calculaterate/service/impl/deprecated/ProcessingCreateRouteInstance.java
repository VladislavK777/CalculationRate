package com.uraltranscom.calculaterate.service.impl.deprecated;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 24.12.2018
 */
/*

@Component
public class ProcessingCreateRouteInstance {
    private final Logger logger = LoggerFactory.getLogger(ProcessingCreateRouteInstance.class);

    public Route getRouteInstance(Station stationDeparture, Station stationDestination, String distance, int volumeWagon, Cargo cargo, RouteType routeType, int countDaysLoadAndUnload, boolean flagNeedCalc) {
        logger.info("Start process create Route");

        Route route = new Route(
                stationDeparture,
                stationDestination,
                distance,
                volumeWagon,
                cargo,
                routeType,
                (int) Math.ceil(Integer.parseInt(distance) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distance))),
                countDaysLoadAndUnload,
                flagNeedCalc
        );

        logger.debug("Route: {}", route.toString());
        return route;
    }
}
*/
