package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.dao.GetDistanceBetweenStationsDAO;
import com.uraltranscom.calculaterate.dao.GetReturnStationDAO;
import com.uraltranscom.calculaterate.dao.GetStationInfoDAO;
import com.uraltranscom.calculaterate.dao.GetTypeOfCargoDAO;
import com.uraltranscom.calculaterate.model.*;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.uraltranscom.calculaterate.util.GetVolumeGroup.getVolumeGroup;
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
@Getter
public class CommonLogicClass {
    private final Logger logger = LoggerFactory.getLogger(CommonLogicClass.class);

    @Autowired
    private ProcessingCreateRouteInstance processingCreateRouteInstance;
    @Autowired
    private GetStationInfoDAO getStationInfoDAO;
    @Autowired
    private GetTypeOfCargoDAO getTypeOfCargo;
    @Autowired
    private GetDistanceBetweenStationsDAO getDistanceBetweenStations;
    @Autowired
    private GetReturnStationDAO getReturnStationDAO;
    @Autowired
    private GetTariff getTariff;
    @Autowired
    private ExceptionReturnRoute exceptionReturnRoute;

    TotalModel totalModel = null;

    public void startLogic(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);

        // Получаем информацию о станция главноего рейса
        Station stationDeparture = getStationInfoDAO.getObject(prepareMapWithParams(idStationDeparture));
        Station stationDestination = getStationInfoDAO.getObject(prepareMapWithParams(idStationDestination));
        Distance distance = getDistanceBetweenStations.getObject(prepareMapWithParams(idStationDeparture, idStationDestination, idCargo));
        Cargo cargo = getTypeOfCargo.getObject(prepareMapWithParams(idCargo));

        // 120, 138 - БЧ-ОКТ ОКТ-БЧ+Прибалтика+Клнрг
        if ((getVolumeGroup(volumeWagon) == 120 || getVolumeGroup(volumeWagon) == 138) &&
                (stationDeparture.getRoad().getIdRoad().equals("3") && stationDestination.getRoad().getIdRoad().equals("22")) ||
                (stationDeparture.getRoad().getIdRoad().equals("22") && stationDestination.getRoad().getIdRoad().equals("3")) ||
                JavaHelperBase.LIST_ROADS_PRIBALT.contains(stationDestination.getRoad().getIdRoad())) {
            List<Route> routeList = new ArrayList<>();
            Route headRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE, JavaHelperBase.LOADING_WAGON, true);
            routeList.add(headRoute);
            double tariff = (Double) getTariff.getTariff(distance, idCargo).get(0);
            Route returnRoute = processingCreateRouteInstance.getRouteInstance(stationDestination, stationDeparture, distance.getDistance(), volumeWagon, cargo, RouteType.EMPTY_ROUTE, JavaHelperBase.UNLOADING_WAGON,false);
            returnRoute.setTariff(tariff);
            routeList.add(returnRoute);
            totalModel = new TotalModel(routeList);

        } else if (JavaHelperBase.LIST_ROADS_WITHOUT_CHECK.contains(stationDeparture.getRoad().getIdRoad())) {
            List<Route> routeList = new ArrayList<>();
            Route headRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE, JavaHelperBase.LOADING_WAGON,true);
            routeList.add(headRoute);
            routeList = exceptionReturnRoute.getListExceptionReturnRoutes(routeList, stationDestination, cargo, volumeWagon);
            totalModel = new TotalModel(routeList);

        } else if (JavaHelperBase.LIST_ROADS_PRIBALT.contains(stationDeparture.getRoad().getIdRoad())) {
            List<Route> routeList = new ArrayList<>();
            if ((getVolumeGroup(volumeWagon) == 120 || getVolumeGroup(volumeWagon) == 138) && stationDestination.getRoad().getIdRoad().equals("22")) {
                Route headRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE, JavaHelperBase.LOADING_WAGON, true);
                routeList.add(headRoute);
                double tariff = (Double) getTariff.getTariff(distance, idCargo).get(0);
                Route returnRoute = processingCreateRouteInstance.getRouteInstance(stationDestination, stationDeparture, distance.getDistance(), volumeWagon, cargo, RouteType.EMPTY_ROUTE, JavaHelperBase.UNLOADING_WAGON,false);
                returnRoute.setTariff(tariff);
                routeList.add(returnRoute);
                totalModel = new TotalModel(routeList);
            }
            // TODO переделать
            else {
                Station stationDepartureFull = getStationInfoDAO.getObject(prepareMapWithParams("273501"));
                Station stationDestinationFull = getStationInfoDAO.getObject(prepareMapWithParams("037202"));
                Cargo cargoFull = getTypeOfCargo.getObject(prepareMapWithParams("094076"));
                Distance distanceFull = new Distance("", "", "1236", "", "");

                Route firstRouteFull = processingCreateRouteInstance.getRouteInstance(stationDepartureFull, stationDestinationFull, distanceFull.getDistance(), volumeWagon, cargoFull, RouteType.FULL_ROUTE, JavaHelperBase.LOADING_WAGON,false);
                firstRouteFull.setNewCountDays(6);
                firstRouteFull.setRate(52000.00);
                routeList.add(firstRouteFull);

                // Получить тариф порожнего рейса
                Distance distanceFirstEmpty = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestinationFull.getIdStation(), stationDeparture.getIdStation(), cargoFull.getIdCargo()));
                double tariffFirstEmpty = (Double) getTariff.getTariff(distanceFirstEmpty, cargoFull.getIdCargo()).get(0);
                Route routeFirstEmpty = processingCreateRouteInstance.getRouteInstance(stationDestinationFull, stationDeparture, distanceFirstEmpty.getDistance(), volumeWagon, cargoFull, RouteType.EMPTY_ROUTE, JavaHelperBase.UNLOADING_WAGON,false);
                routeFirstEmpty.setTariff(tariffFirstEmpty);
                routeList.add(routeFirstEmpty);

                // Create headRouteFull
                Route headRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE, JavaHelperBase.LOADING_2_WAGON,true);
                routeList.add(headRoute);
                routeList = exceptionReturnRoute.getListExceptionReturnRoutes(routeList, stationDestination, cargo, volumeWagon);
                totalModel = new TotalModel(routeList);
            }


        } else if (JavaHelperBase.LIST_STATIONS_KBSH_ROAD.contains(stationDeparture.getIdStation())) {
            List<Route> routeList = new ArrayList<>();
            Station stationDepartureFull = getStationInfoDAO.getObject(prepareMapWithParams("924605"));
            Station stationDestinationFull = getStationInfoDAO.getObject(prepareMapWithParams("648908"));
            Cargo cargoFull = getTypeOfCargo.getObject(prepareMapWithParams("131071"));
            Distance distanceFull = new Distance("", "", "3678", "", "");

            Route firstRouteFull = processingCreateRouteInstance.getRouteInstance(stationDepartureFull, stationDestinationFull, distanceFull.getDistance(), volumeWagon, cargoFull, RouteType.FULL_ROUTE, JavaHelperBase.LOADING_WAGON,false);
            firstRouteFull.setNewCountDays(11);
            firstRouteFull.setRate(44365.48);
            routeList.add(firstRouteFull);

            // Получить тариф порожнего рейса
            Distance distanceFirstEmpty = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestinationFull.getIdStation(), stationDeparture.getIdStation(), cargoFull.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff.getTariff(distanceFirstEmpty, cargoFull.getIdCargo()).get(0);
            Route routeFirstEmpty = processingCreateRouteInstance.getRouteInstance(stationDestinationFull, stationDeparture, distanceFirstEmpty.getDistance(), volumeWagon, cargoFull, RouteType.EMPTY_ROUTE, JavaHelperBase.UNLOADING_WAGON,false);
            routeFirstEmpty.setTariff(tariffFirstEmpty);
            routeList.add(routeFirstEmpty);

            // Create headRouteFull
            Route headRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE, JavaHelperBase.LOADING_2_WAGON,true);
            routeList.add(headRoute);
            routeList = exceptionReturnRoute.getListExceptionReturnRoutes(routeList, stationDestination, cargo, volumeWagon);
            totalModel = new TotalModel(routeList);
        }
    }
}
