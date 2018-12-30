package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.*;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    GetTariffImpl getTariffImpl;
    @Autowired
    GetReturnStation getReturnStation;

    public void startLogic(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);

        // Получаем информацию о станция главноего рейса
        Station stationDeparture = getStationInfo.getObject(prepareMapWithParams(idStationDeparture));
        Station stationDestination = getStationInfo.getObject(prepareMapWithParams(idStationDestination));
        Distance distance = getDistanceBetweenStations.getObject(prepareMapWithParams(idStationDeparture, idStationDestination, idCargo));
        Cargo cargo = getTypeOfCargo.getObject(prepareMapWithParams(idCargo));

        // 120, 138 - БЧ-ОКТ ОКТ-БЧ+Прибалтика+Клнрг
        if ((volumeWagon == 114 || volumeWagon == 120 || volumeWagon == 122 || volumeWagon == 138 || volumeWagon == 140) &&
                (stationDeparture.getRoadStation().getIdRoad().equals("3") && stationDestination.getRoadStation().getIdRoad().equals("22")) ||
                (stationDeparture.getRoadStation().getIdRoad().equals("22") && stationDestination.getRoadStation().getIdRoad().equals("3")) ||
                JavaHelperBase.LIST_ROADS_PRIBALT.contains(stationDestination.getRoadStation().getNameRoad())) {
            Route headRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE);
            headRoute.setCountDaysLoadUnload(headRoute.getCountDays() + JavaHelperBase.LOADING_WAGON);
            double tariff = (Double) getTariff(distance, idCargo).get(0);
            Route returnRoute = processingCreateRouteInstance.getRouteInstance(stationDestination, stationDeparture, distance.getDistance(), volumeWagon, cargo, RouteType.EMPTY_ROUTE);
            returnRoute.setCountDaysLoadUnload(returnRoute.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            returnRoute.setTariff(tariff);
        } else if (JavaHelperBase.LIST_ROADS_WITHOUT_CHECK_DIST.contains(stationDeparture.getRoadStation().getNameRoad())) {
            Route firstRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE);
            firstRoute.setCountDaysLoadUnload(firstRoute.getCountDays() + JavaHelperBase.LOADING_WAGON);

            // TODO перенести в отдельный метод
            String returnStation = getReturnStation.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationDestination.getRoadStation().getIdRoad(), volumeWagon, idCargo));
            Station returnStationInfo = getStationInfo.getObject(prepareMapWithParams(returnStation));
            Distance distanceReturnRoute = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), returnStation, idCargo));
            double tariffReturnRoute = (Double) getTariff(distanceReturnRoute, idCargo).get(0);
            if (stationDestination.getRoadStation().getIdRoad().equals("21")) {
                Route returnRoute = processingCreateRouteInstance.getRouteInstance(stationDestination, returnStationInfo, distanceReturnRoute.getDistance(), volumeWagon, cargo, RouteType.EMPTY_ROUTE);
                returnRoute.setCountDaysLoadUnload(returnRoute.getCountDays() + JavaHelperBase.UNLOADING_WAGON + 14);
                returnRoute.setTariff(tariffReturnRoute + 23000.00);
            }

        } else if (JavaHelperBase.LIST_ROADS_PRIBALT.contains(stationDeparture.getRoadStation().getNameRoad())) {
            if ((volumeWagon == 114 || volumeWagon == 120 || volumeWagon == 122 || volumeWagon == 138 || volumeWagon == 140) && stationDestination.getRoadStation().getIdRoad().equals("22")) {
                Route headRoute = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE);
                headRoute.setCountDaysLoadUnload(headRoute.getCountDays() + JavaHelperBase.LOADING_WAGON);
                double tariff = (Double) getTariff(distance, idCargo).get(0);
                Route returnRoute = processingCreateRouteInstance.getRouteInstance(stationDestination, stationDeparture, distance.getDistance(), volumeWagon, cargo, RouteType.EMPTY_ROUTE);
                returnRoute.setCountDaysLoadUnload(returnRoute.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
                returnRoute.setTariff(tariff);
            }
            // TODO переделать
            Station stationDepartureFull = getStationInfo.getObject(prepareMapWithParams("273501"));
            Station stationDestinationFull = getStationInfo.getObject(prepareMapWithParams("037202"));
            Cargo cargoFull = getTypeOfCargo.getObject(prepareMapWithParams("094076"));
            Distance distanceFull = new Distance("", "", "1236", "", "");

            Route firstRouteFull = processingCreateRouteInstance.getRouteInstance(stationDepartureFull, stationDestinationFull, distanceFull.getDistance(), volumeWagon, cargoFull, RouteType.FULL_ROUTE);
            firstRouteFull.setCountDaysLoadUnload(firstRouteFull.getCountDays() + JavaHelperBase.LOADING_WAGON);
            firstRouteFull.setRate(52000.00);

            // Получить тариф порожнего рейса
            Distance distanceFirstEmpty = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestinationFull.getIdStation(), stationDeparture.getIdStation(), cargoFull.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff(distanceFirstEmpty, cargoFull.getIdCargo()).get(0);
            Route routeFirstEmpty = processingCreateRouteInstance.getRouteInstance(stationDestinationFull, stationDeparture, distanceFirstEmpty.getDistance(), volumeWagon, cargoFull, RouteType.EMPTY_ROUTE);
            routeFirstEmpty.setCountDaysLoadUnload(routeFirstEmpty.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeFirstEmpty.setTariff(tariffFirstEmpty);

            // Create headRouteFull
            Route headRouteFull = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE);
            headRouteFull.setCountDaysLoadUnload(headRouteFull.getCountDays() + JavaHelperBase.LOADING_2_WAGON);

            // Finish route
            // stationDestination
            // stationOpornic
            // distance
            // cargo
            // volume
            // type Route Empty
            // tariff
            // route.setFull(getDays() + 5)
            // route.setTariff(tariff)

        } else if (JavaHelperBase.LIST_STATIONS_KBSH_ROAD.contains(stationDeparture.getIdStation())) {
            Station stationDepartureFull = getStationInfo.getObject(prepareMapWithParams("924605"));
            Station stationDestinationFull =getStationInfo.getObject(prepareMapWithParams("648908"));
            Cargo cargoFull = getTypeOfCargo.getObject(prepareMapWithParams("131071"));
            Distance distanceFull = new Distance("3678", "", "", "", "");

            Route firstRouteFull = processingCreateRouteInstance.getRouteInstance(stationDepartureFull, stationDestinationFull, distanceFull.getDistance(), volumeWagon, cargoFull, RouteType.FULL_ROUTE);
            firstRouteFull.setCountDaysLoadUnload(firstRouteFull.getCountDays() + JavaHelperBase.LOADING_WAGON);
            firstRouteFull.setRate(44365.48);

            // Получить тариф порожнего рейса
            Distance distanceFirstEmpty = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestinationFull.getIdStation(), stationDeparture.getIdStation(), cargoFull.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff(distanceFirstEmpty, cargoFull.getIdCargo()).get(0);
            Route routeFirstEmpty = processingCreateRouteInstance.getRouteInstance(stationDestinationFull, stationDeparture, distanceFirstEmpty.getDistance(), volumeWagon, cargoFull, RouteType.EMPTY_ROUTE);
            routeFirstEmpty.setCountDaysLoadUnload(routeFirstEmpty.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeFirstEmpty.setTariff(tariffFirstEmpty);

            // Create headRouteFull
            Route headRouteFull = processingCreateRouteInstance.getRouteInstance(stationDeparture, stationDestination, distance.getDistance(), volumeWagon, cargo, RouteType.FULL_ROUTE);
            headRouteFull.setCountDaysLoadUnload(headRouteFull.getCountDays() + JavaHelperBase.LOADING_2_WAGON);

            // Finish route
            // stationDestination
            // stationOpornic
            // distance
            // cargo
            // volume
            // type Route Empty
            // tariff
            // route.setFull(getDays() + 5)
            // route.setTariff(tariff)
        }
    }

    protected List<Object> getTariff(Distance distanceInfo, String idCargo) {
        double tariff = 0.00;
        boolean isLoadingTariffFromDB = false;
        String [] codeCountryArray = distanceInfo.getRouteCountries().split("/");
        List<Integer> listFlag = new ArrayList<>();
        List<Object> result = new ArrayList<>();
        if (codeCountryArray.length == 1) {
            tariff = getTariffImpl.getObject(prepareMapWithParams(
                    Integer.parseInt(codeCountryArray[0]),
                    Integer.parseInt(distanceInfo.getDistance()),
                    idCargo)).getTariff();

        } else {
            String [] distanceTransitArray;
            List<Integer> distanceList = new ArrayList<>();
            int distanceStart = Integer.parseInt(distanceInfo.getDistanceStart());
            int distanceEnd = Integer.parseInt(distanceInfo.getDistanceEnd());
            distanceList.add(distanceStart);
            if (!distanceInfo.getRouteDistance().isEmpty()) {
                distanceTransitArray = distanceInfo.getRouteDistance().split("/");
                for (String s: distanceTransitArray) {
                    distanceList.add(Integer.parseInt(s));
                }
            }
            distanceList.add(distanceEnd);
            for (int i = 0; i < codeCountryArray.length; i++) {
                Tariff tariffFull = getTariffImpl.getObject(prepareMapWithParams(
                        Integer.parseInt(codeCountryArray[i]),
                        distanceList.get(i),
                        idCargo));
                tariff = tariff + tariffFull.getTariff();
                listFlag.add((int)tariffFull.getFlagDownloadFromDB());
            }
        }
        tariff = Math.round(tariff* 100) / 100.00d;
        isLoadingTariffFromDB = listFlag.contains(1);
        result.add(tariff);
        result.add(isLoadingTariffFromDB);
        return result;
    }
}
