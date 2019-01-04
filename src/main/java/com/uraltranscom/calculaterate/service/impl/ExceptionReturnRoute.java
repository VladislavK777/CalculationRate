package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.*;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.uraltranscom.calculaterate.util.PrepareMapParams.prepareMapWithParams;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 30.12.2018
 */

@Component
public class ExceptionReturnRoute extends GetObject {
    private final Logger logger = LoggerFactory.getLogger(ExceptionReturnRoute.class);

    List<Route> getListExceptionReturnRoutes(Station stationDestination, Cargo cargo, int volumeWagon) {
        // Если станция назначения Крым
        if (stationDestination.getRoad().getIdRoad().equals("21")) {
            List<Route> routeList = new ArrayList<>();
            String returnStation = getReturnStationDAO.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationDestination.getRoad().getIdRoad(), volumeWagon, cargo.getIdCargo()));
            Station returnStationInfo = getStationInfoDAO.getObject(prepareMapWithParams(returnStation));
            Distance distanceReturnRoute = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), returnStation, cargo.getIdCargo()));
            double tariffReturnRoute = (Double) getTariff.getTariff(distanceReturnRoute, cargo.getIdCargo()).get(0);
            Route returnRoute = processingCreateRouteInstance.getRouteInstance(stationDestination, returnStationInfo, distanceReturnRoute.getDistance(), volumeWagon, cargo, RouteType.EMPTY_ROUTE);
            returnRoute.setCountDaysLoadUnload(returnRoute.getCountDays() + JavaHelperBase.UNLOADING_WAGON + 14);
            returnRoute.setTariff(tariffReturnRoute + (-23000.00));
            routeList.add(returnRoute);
            return routeList;
        }
        // Если станция назначения УТИ или ТРК 138 или ТДЖ 138
        else if (stationDestination.getRoad().getIdRoad().equals("32") ||
                ((stationDestination.getRoad().getIdRoad().equals("30") || stationDestination.getRoad().getIdRoad().equals("31")) &&
                        (volumeWagon == 138 || volumeWagon == 140))) {
            List<Route> routeList = new ArrayList<>();
            Cargo cargoRoute = getTypeOfCargo.getObject(prepareMapWithParams("489179"));

            // Получаем тариф порожнего рейса от станции назначения и маршрут
            Station stationEmptyFirst = getStationInfoDAO.getObject(prepareMapWithParams("674300"));
            Distance distanceEmptyFirst = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationEmptyFirst.getIdStation(), cargoRoute.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff.getTariff(distanceEmptyFirst, cargoRoute.getIdCargo()).get(0);
            Route routeEmptyFirst = processingCreateRouteInstance.getRouteInstance(stationDestination, stationEmptyFirst, distanceEmptyFirst.getDistance(), volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptyFirst.setCountDaysLoadUnload(routeEmptyFirst.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptyFirst.setTariff(tariffFirstEmpty);
            routeList.add(routeEmptyFirst);

            // Получаем второй маршрут
            Station stationFullSecond = getStationInfoDAO.getObject(prepareMapWithParams("230309"));
            Route routeFullSecond = processingCreateRouteInstance.getRouteInstance(stationEmptyFirst, stationFullSecond, "3096", volumeWagon, cargoRoute, RouteType.FULL_ROUTE);
            routeFullSecond.setCountDays(15);
            routeFullSecond.setCountDaysLoadUnload(routeFullSecond.getCountDays() + JavaHelperBase.LOADING_2_WAGON);
            routeFullSecond.setRate(14000.00);
            routeList.add(routeFullSecond);

            // Получаем маршрут второго порожнего рейса до опорной станции
            Station stationEmptySecond = getStationInfoDAO.getObject(prepareMapWithParams("195800"));
            Route routeEmptySecond = processingCreateRouteInstance.getRouteInstance(stationFullSecond, stationEmptySecond, "311", volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptySecond.setCountDays(2);
            routeEmptySecond.setCountDaysLoadUnload(routeEmptySecond.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptySecond.setTariff(-12789.00);
            routeList.add(routeEmptySecond);
            return routeList;
        }
        // Если станция назначения ТРК 150/120 или ТДЖ 150/120
        else if ((stationDestination.getRoad().getIdRoad().equals("30") || stationDestination.getRoad().getIdRoad().equals("31")) &&
                        (volumeWagon == 114 || volumeWagon == 120 || volumeWagon == 122 || volumeWagon == 150 || volumeWagon == 158 || volumeWagon == 161)) {
            List<Route> routeList = new ArrayList<>();
            Cargo cargoRoute = getTypeOfCargo.getObject(prepareMapWithParams("481603"));

            // Получаем тариф порожнего рейса от станции назначения и маршрут
            Station stationEmptyFirst = getStationInfoDAO.getObject(prepareMapWithParams("667805"));
            Distance distanceEmptyFirst = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationEmptyFirst.getIdStation(), cargoRoute.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff.getTariff(distanceEmptyFirst, cargoRoute.getIdCargo()).get(0);
            Route routeEmptyFirst = processingCreateRouteInstance.getRouteInstance(stationDestination, stationEmptyFirst, distanceEmptyFirst.getDistance(), volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptyFirst.setCountDaysLoadUnload(routeEmptyFirst.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptyFirst.setTariff(tariffFirstEmpty);
            routeList.add(routeEmptyFirst);

            // Получаем второй маршрут
            Station stationFullSecond = getStationInfoDAO.getObject(prepareMapWithParams("035601"));
            Route routeFullSecond = processingCreateRouteInstance.getRouteInstance(stationEmptyFirst, stationFullSecond, "2672", volumeWagon, cargoRoute, RouteType.FULL_ROUTE);
            routeFullSecond.setCountDays(11);
            routeFullSecond.setCountDaysLoadUnload(routeFullSecond.getCountDays() + JavaHelperBase.LOADING_2_WAGON);
            routeFullSecond.setRate(45697.00);
            routeList.add(routeFullSecond);

            // Получаем маршрут второго порожнего рейса до опорной станции
            Station stationEmptySecond = getStationInfoDAO.getObject(prepareMapWithParams("010906"));
            Route routeEmptySecond = processingCreateRouteInstance.getRouteInstance(stationFullSecond, stationEmptySecond, "476", volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptySecond.setCountDays(3);
            routeEmptySecond.setCountDaysLoadUnload(routeEmptySecond.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptySecond.setTariff(-14746.00);
            routeList.add(routeEmptySecond);
            return routeList;
        }
        // Если станция назначения ДВС и класс груза 2 или 3
        else if (stationDestination.getRoad().getIdRoad().equals("15") && (cargo.getCargoType().equals("2") || cargo.getCargoType().equals("3"))) {
            List<Route> routeList = new ArrayList<>();
            Cargo cargoRoute = getTypeOfCargo.getObject(prepareMapWithParams("542239"));
            // Получаем тариф порожнего рейса от станции назначения и маршрут
            Station stationEmptyFirst = getStationInfoDAO.getObject(prepareMapWithParams("988306"));
            Distance distanceEmptyFirst = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationEmptyFirst.getIdStation(), cargoRoute.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff.getTariff(distanceEmptyFirst, cargoRoute.getIdCargo()).get(0);
            Route routeEmptyFirst = processingCreateRouteInstance.getRouteInstance(stationDestination, stationEmptyFirst, distanceEmptyFirst.getDistance(), volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptyFirst.setCountDaysLoadUnload(routeEmptyFirst.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptyFirst.setTariff(tariffFirstEmpty);
            routeList.add(routeEmptyFirst);

            // Получаем второй маршрут
            Station stationFullSecond = getStationInfoDAO.getObject(prepareMapWithParams("800101"));
            Route routeFullSecond = processingCreateRouteInstance.getRouteInstance(stationEmptyFirst, stationFullSecond, "7277", volumeWagon, cargoRoute, RouteType.FULL_ROUTE);
            routeFullSecond.setCountDays(18);
            routeFullSecond.setCountDaysLoadUnload(routeFullSecond.getCountDays() + JavaHelperBase.LOADING_2_WAGON);
            routeFullSecond.setRate(1694.92);
            routeList.add(routeFullSecond);

            // Получаем маршрут второго порожнего рейса до опорной станции
            Station stationEmptySecond = getStationInfoDAO.getObject(prepareMapWithParams("806708"));
            Route routeEmptySecond = processingCreateRouteInstance.getRouteInstance(stationFullSecond, stationEmptySecond, "226", volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptySecond.setCountDays(3);
            routeEmptySecond.setCountDaysLoadUnload(routeEmptySecond.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptySecond.setTariff(-6048.00);
            routeList.add(routeEmptySecond);
            return routeList;
        }
        // Если станция назначения ОКТ группа 120 или СЕВ группа 120
        else if ((stationDestination.getRoad().getIdRoad().equals("20") || stationDestination.getRoad().getIdRoad().equals("26")) &&
                (volumeWagon == 114 || volumeWagon == 120 || volumeWagon == 122)) {
            List<Route> routeList = new ArrayList<>();
            Cargo cargoRoute = getTypeOfCargo.getObject(prepareMapWithParams("122022"));
            // Получаем тариф порожнего рейса от станции назначения и маршрут
            Station stationEmptyFirst = getStationInfoDAO.getObject(prepareMapWithParams("011400"));
            Distance distanceEmptyFirst = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationEmptyFirst.getIdStation(), cargoRoute.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff.getTariff(distanceEmptyFirst, cargoRoute.getIdCargo()).get(0);
            Route routeEmptyFirst = processingCreateRouteInstance.getRouteInstance(stationDestination, stationEmptyFirst, distanceEmptyFirst.getDistance(), volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptyFirst.setCountDaysLoadUnload(routeEmptyFirst.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptyFirst.setTariff(tariffFirstEmpty);
            routeList.add(routeEmptyFirst);

            // Получаем второй маршрут
            Station stationFullSecond = getStationInfoDAO.getObject(prepareMapWithParams("548004"));
            Route routeFullSecond = processingCreateRouteInstance.getRouteInstance(stationEmptyFirst, stationFullSecond, "3728", volumeWagon, cargoRoute, RouteType.FULL_ROUTE);
            routeFullSecond.setCountDays(11);
            routeFullSecond.setCountDaysLoadUnload(routeFullSecond.getCountDays() + JavaHelperBase.LOADING_2_WAGON);
            routeFullSecond.setRate(130000.00);
            routeList.add(routeFullSecond);

            // Получаем маршрут второго порожнего рейса до опорной станции
            Station stationEmptySecond = getStationInfoDAO.getObject(prepareMapWithParams("612003"));
            Route routeEmptySecond = processingCreateRouteInstance.getRouteInstance(stationFullSecond, stationEmptySecond, "1360", volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptySecond.setCountDays(7);
            routeEmptySecond.setCountDaysLoadUnload(routeEmptySecond.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptySecond.setTariff(-44909.00);
            routeList.add(routeEmptySecond);
            return routeList;
        }
        // Если станция назначения ВСБ группа 120 или ЗАБ группа 120
        else if ((stationDestination.getRoad().getIdRoad().equals("11") || stationDestination.getRoad().getIdRoad().equals("17")) &&
                (volumeWagon == 114 || volumeWagon == 120 || volumeWagon == 122)) {
            List<Route> routeList = new ArrayList<>();
            Cargo cargoRoute = getTypeOfCargo.getObject(prepareMapWithParams("531037"));
            // Получаем тариф порожнего рейса от станции назначения и маршрут
            Station stationEmptyFirst = getStationInfoDAO.getObject(prepareMapWithParams("930305"));
            Distance distanceEmptyFirst = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationEmptyFirst.getIdStation(), cargoRoute.getIdCargo()));
            double tariffFirstEmpty = (Double) getTariff.getTariff(distanceEmptyFirst, cargoRoute.getIdCargo()).get(0);
            Route routeEmptyFirst = processingCreateRouteInstance.getRouteInstance(stationDestination, stationEmptyFirst, distanceEmptyFirst.getDistance(), volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptyFirst.setCountDaysLoadUnload(routeEmptyFirst.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptyFirst.setTariff(tariffFirstEmpty);
            routeList.add(routeEmptyFirst);

            // Получаем второй маршрут
            Station stationFullSecond = getStationInfoDAO.getObject(prepareMapWithParams("310109"));
            Route routeFullSecond = processingCreateRouteInstance.getRouteInstance(stationEmptyFirst, stationFullSecond, "4686", volumeWagon, cargoRoute, RouteType.FULL_ROUTE);
            routeFullSecond.setCountDaysLoadUnload( + JavaHelperBase.LOADING_2_WAGON);
            routeFullSecond.setRate(13559.32);
            routeList.add(routeFullSecond);

            // Получаем маршрут второго порожнего рейса до опорной станции
            Station stationEmptySecond = getStationInfoDAO.getObject(prepareMapWithParams("195800"));
            Route routeEmptySecond = processingCreateRouteInstance.getRouteInstance(stationFullSecond, stationEmptySecond, "320", volumeWagon, cargoRoute, RouteType.EMPTY_ROUTE);
            routeEmptySecond.setCountDaysLoadUnload(routeEmptySecond.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            routeEmptySecond.setTariff(-6759.00);
            routeList.add(routeEmptySecond);
            return routeList;
        }
        //TODO пока тут, потом перенести
        // Если нет в исключениях, то добавляем рейс до станции возврата
        else {
            List<Route> routeList = new ArrayList<>();
            String returnStation = getReturnStationDAO.getObject(prepareMapWithParams(stationDestination.getIdStation(), stationDestination.getRoad().getIdRoad(), volumeWagon, cargo.getIdCargo()));
            Station returnStationInfo = getStationInfoDAO.getObject(prepareMapWithParams(returnStation));
            Distance distanceReturnRoute = getDistanceBetweenStations.getObject(prepareMapWithParams(stationDestination.getIdStation(), returnStation, cargo.getIdCargo()));
            double tariffReturnRoute = (Double) getTariff.getTariff(distanceReturnRoute, cargo.getIdCargo()).get(0);
            Route returnRoute = processingCreateRouteInstance.getRouteInstance(stationDestination, returnStationInfo, distanceReturnRoute.getDistance(), volumeWagon, cargo, RouteType.EMPTY_ROUTE);
            returnRoute.setCountDaysLoadUnload(returnRoute.getCountDays() + JavaHelperBase.UNLOADING_WAGON);
            returnRoute.setTariff(tariffReturnRoute);
            routeList.add(returnRoute);
            return routeList;
        }
    }
}
