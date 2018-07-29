package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.Wagon;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.dynamicdistributionpark.service.BasicClassLookingFor;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Основной класс
 * Implementation for {@link BasicClassLookingFor} interface
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

@Service
public class BasicClassLookingForImpl extends JavaHelperBase implements BasicClassLookingFor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicClassLookingForImpl.class);

    @Autowired
    private GetListOfDistanceImpl getListOfDistance;
    @Autowired
    private ClassHandlerLookingForImpl classHandlerLookingFor;
    @Autowired
    private GetListOfEmptyRoutesImpl getListOfEmptyRoutes;
    @Autowired
    private GetListOfRatesImpl getListOfRates;
    @Autowired
    private ClassHandlerTotalCalculateImpl classHandlerTotalCalculate;

    // Мапа для записи в файл Вагона + Станция назначения.
    private Map<String, WagonFinalInfo> totalMapWithWagonNumberAndRoute = new HashMap<>();

    // Массив распределенных маршрутов и вагонов
    private List<WagonFinalInfo> listOfDistributedRoutesAndWagons = new ArrayList<>();

    // Массив ошибок
    private List<String> listOfError = new ArrayList<>();

    // Флаг, что нужно заполнить или проверить ставку или тариф
    private boolean isFlag = false;

    private BasicClassLookingForImpl() {
    }

    @Override
    public void fillMapRouteIsOptimal() {
        // Очищаем массивы итоговые
        totalMapWithWagonNumberAndRoute.clear();
        listOfDistributedRoutesAndWagons.clear();
        listOfError.clear();

        // Запускаем метод заполненеия первоначальной мапы расстояний
        getListOfDistance.fillMap();

        // Заполняем мапы
        Map<Integer, Route> tempMapRoutes = getListOfDistance.getGetListOfRoutesImpl().getMapOfRoutes();
        List<Wagon> tempListOfWagons = getListOfDistance.getGetListOfWagonsImpl().getListOfWagons();

        // Запускаем распределение
        if (!tempMapRoutes.isEmpty()) {
            classHandlerLookingFor.lookingForOptimalMapOfRoute(tempMapRoutes, tempListOfWagons);
        }

        // очищаем мапы
        tempListOfWagons.clear();
        tempMapRoutes.clear();
    }

    public Map<String, WagonFinalInfo> getTotalMapWithWagonNumberAndRoute() {
        return totalMapWithWagonNumberAndRoute;
    }

    public void setTotalMapWithWagonNumberAndRoute(Map<String, WagonFinalInfo> totalMapWithWagonNumberAndRoute) {
        this.totalMapWithWagonNumberAndRoute = totalMapWithWagonNumberAndRoute;
    }

    public List<WagonFinalInfo> getListOfDistributedRoutesAndWagons() {
        return listOfDistributedRoutesAndWagons;
    }

    public void setListOfDistributedRoutesAndWagons(List<WagonFinalInfo> listOfDistributedRoutesAndWagons) {
        this.listOfDistributedRoutesAndWagons = listOfDistributedRoutesAndWagons;
    }

    public List<String> getListOfError() {
        return listOfError;
    }

    public void setListOfError(List<String> listOfError) {
        this.listOfError = listOfError;
    }

    public GetListOfDistanceImpl getGetListOfDistance() {
        return getListOfDistance;
    }

    public void setGetListOfDistance(GetListOfDistanceImpl getListOfDistance) {
        this.getListOfDistance = getListOfDistance;
    }

    public GetListOfEmptyRoutesImpl getGetListOfEmptyRoutes() {
        return getListOfEmptyRoutes;
    }

    public void setGetListOfEmptyRoutes(GetListOfEmptyRoutesImpl getListOfEmptyRoutes) {
        this.getListOfEmptyRoutes = getListOfEmptyRoutes;
    }

    public GetListOfRatesImpl getGetListOfRates() {
        return getListOfRates;
    }

    public void setGetListOfRates(GetListOfRatesImpl getListOfRates) {
        this.getListOfRates = getListOfRates;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public ClassHandlerLookingForImpl getClassHandlerLookingFor() {
        return classHandlerLookingFor;
    }

    public void setClassHandlerLookingFor(ClassHandlerLookingForImpl classHandlerLookingFor) {
        this.classHandlerLookingFor = classHandlerLookingFor;
    }

    public ClassHandlerTotalCalculateImpl getClassHandlerTotalCalculate() {
        return classHandlerTotalCalculate;
    }

    public void setClassHandlerTotalCalculate(ClassHandlerTotalCalculateImpl classHandlerTotalCalculate) {
        this.classHandlerTotalCalculate = classHandlerTotalCalculate;
    }
}