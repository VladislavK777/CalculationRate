package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.EmptyRoute;
import com.uraltranscom.dynamicdistributionpark.model.RateClass;
import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.Wagon;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalRouteInfo;
import com.uraltranscom.dynamicdistributionpark.service.ClassHandlerLookingFor;
import com.uraltranscom.dynamicdistributionpark.service.additional.CompareMapValue;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import com.uraltranscom.dynamicdistributionpark.service.additional.PrepareDistanceOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Класс-обработчик алгоритма расчета
 * Implementation for {@link ClassHandlerLookingFor} interface
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
@Component
public class ClassHandlerLookingForImpl extends JavaHelperBase implements ClassHandlerLookingFor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerLookingForImpl.class);

    @Autowired
    private GetListOfDistanceImpl getListOfDistance;
    @Autowired
    private GetFullMonthCircleOfWagonImpl getFullMonthCircleOfWagonImpl;
    @Autowired
    private BasicClassImpl basicClass;
    @Autowired
    private GetListOfEmptyRoutesImpl getListOfEmptyRoutes;
    @Autowired
    private GetListOfRatesImpl getListOfRates;
    @Autowired
    private GetRateImpl getRate;
    @Autowired
    private GetTariffImpl getTariff;

    // Итоговая мапа с данными вагона
    private Map<String, WagonFinalInfo> mapFinalWagonInfo = new HashMap<>();
    // Итоговая маппа для вывода списка невостребованных рейсов
    private Map<Route, List<Integer>> mapFinalOrderInfo = new HashMap<>();
    // Временная мапа после отработки метода
    private Map<Integer, Route> tempMapTotalRoute = new HashMap<>();
    // Список невостребованых вагонво
    private List<Wagon> totalListWagon = new ArrayList<>();

    private ClassHandlerLookingForImpl() {
    }

    @Override
    public void lookingForOptimalMapOfRoute() {
        logger.info("Start root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");
        getFullMonthCircleOfWagonImpl.getMapOfDaysOfWagon().clear();

        // Заполняем мапы
        List<Wagon> copyListOfWagon = new ArrayList<>(getListOfDistance.getListOfWagons());
        Map<Integer, Route> tempMapOfRoutes = new HashMap<>(getListOfDistance.getMapOfRoutes());

        // Временная выходная мапа
        Map<String, WagonFinalInfo> tempMapWagonInfo = new HashMap<>();

        // Мапа расстояний
        Map<List<Object>, Integer> mapDistance = new HashMap<>();

        // Запускаем цикл
        Boolean isOk = true;
        while (isOk) {
            isOk = false;
            // Очищаем массивы
            mapDistance.clear();

            for (Wagon _wagons : copyListOfWagon) {
                // Индекс последнего маршрута
                int index = _wagons.getListRoutes().size() - 1;
                // Получаем код станции назначения вагона
                String keyOfStationOfWagonDestination = _wagons.getListRoutes().get(index).getKeyOfStationDestination().trim();

                // По каждому вагону высчитываем расстояние до каждой начальной станнции маршрутов
                // Цикл расчета расстояния и заполнения мапы
                for (Map.Entry<Integer, Route> _routes : tempMapOfRoutes.entrySet()) {

                    List<Object> list = new ArrayList<>();
                    // Станция отправления рейса
                    String keyOfStationDeparture = _routes.getValue().getKeyOfStationDeparture();
                    list.add(_wagons);
                    list.add(_routes.getValue());
                    if (_wagons.getVolume() >= _routes.getValue().getVolumePeriod().getVolumeFrom() && _wagons.getVolume() <= _routes.getValue().getVolumePeriod().getVolumeTo() &&
                            _routes.getValue().getCountOrders() > 0 && !_wagons.getListRoutes().get(index).getKeyOfStationDestination().equals("")) {
                        String key = keyOfStationOfWagonDestination + "_" + keyOfStationDeparture;

                        // Ищем в готовой мапе расстояние
                        //TODO сделать компактнее
                        if (getListOfDistance.getRootMapWithDistances().containsKey(key)) {
                            if (getListOfDistance.getRootMapWithDistances().get(key).get(0) != -20000) {
                                if (getListOfDistance.getRootMapWithDistances().get(key).get(1) == 0) {
                                    if (_wagons.getListRoutes().get(index).getCargo().getCargoType() == 3) {
                                        if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 300) {
                                            mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                        }
                                    } else {
                                        if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 600) {
                                            mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                        }
                                    }
                                } else if (getListOfDistance.getRootMapWithDistances().get(key).get(1) == 1) {
                                    if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 2500) {
                                        mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                    }
                                } else {
                                    if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 1800) {
                                        mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                    }
                                }
                            }
                        } else {
                            if (isCheckMore40(_wagons, _routes.getValue())) {
                                List<Integer> listDistance = getListOfDistance.listDistance(keyOfStationOfWagonDestination, keyOfStationDeparture);
                                if (listDistance != null) {
                                    if (listDistance.get(0) != -20000) {
                                        if (listDistance.get(1) == 0) {
                                            if (_wagons.getListRoutes().get(index).getCargo().getCargoType() == 3) {
                                                if (listDistance.get(0) <= 300) {
                                                    mapDistance.put(list, listDistance.get(0));
                                                }
                                            } else {
                                                if (listDistance.get(0) <= 600) {
                                                    mapDistance.put(list, listDistance.get(0));
                                                }
                                            }
                                        } else if (listDistance.get(1) == 1) {
                                            if (listDistance.get(0) <= 2500) {
                                                mapDistance.put(list, listDistance.get(0));
                                            }
                                        } else {
                                            if (listDistance.get(0) <= 1800) {
                                                mapDistance.put(list, listDistance.get(0));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int indexMap = mapDistance.size();
            CompareMapValue[] compareMapValues = new CompareMapValue[indexMap];
            indexMap = 0;
            for (Map.Entry<List<Object>, Integer> entry : mapDistance.entrySet()) {
                compareMapValues[indexMap++] = new CompareMapValue(entry.getKey(), entry.getValue());
            }
            Arrays.sort(compareMapValues);

            if (compareMapValues.length != 0) {
                List<Object> listRouteMinDistance = compareMapValues[0].list;
                Route route = (Route) listRouteMinDistance.get(1);
                Wagon wagon = (Wagon) listRouteMinDistance.get(0);
                int minDistance = compareMapValues[0].distance;
                int index = wagon.getListRoutes().size() - 1;

                // Число дней пройденных вагоном
                int countCircleDays = getFullMonthCircleOfWagonImpl.fullDays(
                        wagon.getNumberOfWagon(),
                        minDistance,
                        route.getDistanceOfWay());

                // Добавляем информацию в выходную мапу
                if (!tempMapWagonInfo.containsKey(wagon.getNumberOfWagon())) {
                    if (countCircleDays <= MAX_COUNT_DAYS) {
                        List<WagonFinalRouteInfo> listInfo = new ArrayList<>();
                        listInfo.add(new WagonFinalRouteInfo(getFullMonthCircleOfWagonImpl.getListOfDaysOfWagon(wagon.getNumberOfWagon()).get(index),
                                minDistance,
                                wagon.getListRoutes().get(index).getNameOfStationDestination(),
                                wagon.getListRoutes().get(index).getKeyOfStationDestination(),
                                route.getNameOfStationDeparture(),
                                route.getKeyOfStationDeparture(),
                                route,
                                wagon.getListRoutes().get(index).getCargo(),
                                wagon.getListRoutes().get(index).getCargo().getCargoType())
                        );
                        tempMapWagonInfo.put(wagon.getNumberOfWagon(),
                                new WagonFinalInfo(
                                        wagon.getNumberOfWagon(),
                                        listInfo)
                        );
                        // Добавляем информацию у вагона, добавляем новый рейс
                        List<Route> list = wagon.getListRoutes();
                        list.add(route);

                        for (Wagon _wagon : copyListOfWagon) {
                            if (_wagon.getNumberOfWagon().equals(wagon.getNumberOfWagon())) {
                                _wagon.setListRoutes(list);
                            }
                        }

                        // Уменьшаем количество рейсов у маршрута
                        for (Map.Entry<Integer, Route> entry : tempMapOfRoutes.entrySet()) {
                            if (entry.getValue().equals(route)) {
                                tempMapOfRoutes.put(entry.getKey(), new Route(tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDeparture(),
                                        tempMapOfRoutes.get(entry.getKey()).getNameOfStationDeparture(),
                                        tempMapOfRoutes.get(entry.getKey()).getRoadOfStationDeparture(),
                                        tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDestination(),
                                        tempMapOfRoutes.get(entry.getKey()).getNameOfStationDestination(),
                                        tempMapOfRoutes.get(entry.getKey()).getRoadOfStationDestination(),
                                        tempMapOfRoutes.get(entry.getKey()).getDistanceOfWay(),
                                        tempMapOfRoutes.get(entry.getKey()).getCustomer(),
                                        tempMapOfRoutes.get(entry.getKey()).getCountOrders() - 1,
                                        tempMapOfRoutes.get(entry.getKey()).getVolumePeriod().getVolumeFrom(),
                                        tempMapOfRoutes.get(entry.getKey()).getVolumePeriod().getVolumeTo(),
                                        tempMapOfRoutes.get(entry.getKey()).getNumberOrder(),
                                        tempMapOfRoutes.get(entry.getKey()).getCargo().getNameCargo(),
                                        tempMapOfRoutes.get(entry.getKey()).getCargo().getKeyCargo(),
                                        tempMapOfRoutes.get(entry.getKey()).getWagonType().getWagonType()));
                            }
                        }
                        isOk = true;
                    }
                } else {
                    if (countCircleDays <= MAX_COUNT_DAYS_DECADE) {
                        WagonFinalInfo wagonFinalInfo = tempMapWagonInfo.get(wagon.getNumberOfWagon());
                        List<WagonFinalRouteInfo> wagonFinalRouteInfo = wagonFinalInfo.getListRouteInfo();
                        wagonFinalRouteInfo.add(new WagonFinalRouteInfo(getFullMonthCircleOfWagonImpl.getListOfDaysOfWagon(wagon.getNumberOfWagon()).get(index),
                                minDistance,
                                wagon.getListRoutes().get(index).getNameOfStationDestination(),
                                wagon.getListRoutes().get(index).getKeyOfStationDestination(),
                                route.getNameOfStationDeparture(),
                                route.getKeyOfStationDeparture(),
                                route,
                                wagon.getListRoutes().get(index).getCargo(),
                                wagon.getListRoutes().get(index).getCargo().getCargoType())
                        );
                        tempMapWagonInfo.get(wagon.getNumberOfWagon()).setListRouteInfo(wagonFinalRouteInfo);
                        tempMapWagonInfo.get(wagon.getNumberOfWagon()).setSizeArray(wagonFinalRouteInfo.size() - 1);
                        // Добавляем информацию у вагона, добавляем новый рейс
                        List<Route> list = wagon.getListRoutes();
                        list.add(route);

                        for (Wagon _wagon : copyListOfWagon) {
                            if (_wagon.getNumberOfWagon().equals(wagon.getNumberOfWagon())) {
                                _wagon.setListRoutes(list);
                            }
                        }

                        // Уменьшаем количество рейсов у маршрута
                        for (Map.Entry<Integer, Route> entry : tempMapOfRoutes.entrySet()) {
                            if (entry.getValue().equals(route)) {
                                tempMapOfRoutes.put(entry.getKey(), new Route(tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDeparture(),
                                        tempMapOfRoutes.get(entry.getKey()).getNameOfStationDeparture(),
                                        tempMapOfRoutes.get(entry.getKey()).getRoadOfStationDeparture(),
                                        tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDestination(),
                                        tempMapOfRoutes.get(entry.getKey()).getNameOfStationDestination(),
                                        tempMapOfRoutes.get(entry.getKey()).getRoadOfStationDestination(),
                                        tempMapOfRoutes.get(entry.getKey()).getDistanceOfWay(),
                                        tempMapOfRoutes.get(entry.getKey()).getCustomer(),
                                        tempMapOfRoutes.get(entry.getKey()).getCountOrders() - 1,
                                        tempMapOfRoutes.get(entry.getKey()).getVolumePeriod().getVolumeFrom(),
                                        tempMapOfRoutes.get(entry.getKey()).getVolumePeriod().getVolumeTo(),
                                        tempMapOfRoutes.get(entry.getKey()).getNumberOrder(),
                                        tempMapOfRoutes.get(entry.getKey()).getCargo().getNameCargo(),
                                        tempMapOfRoutes.get(entry.getKey()).getCargo().getKeyCargo(),
                                        tempMapOfRoutes.get(entry.getKey()).getWagonType().getWagonType()));
                            }
                        }
                        isOk = true;
                    } else {
                        for (int i = 0; i < copyListOfWagon.size(); i++) {
                            if (copyListOfWagon.get(i).getNumberOfWagon().equals(wagon.getNumberOfWagon())) {
                                copyListOfWagon.remove(i);
                            }
                        }
                        isOk = true;
                    }

                }
            }
        }

        putRateAndTariff(tempMapWagonInfo);
        checkEmptyTariffOrRate(mapFinalWagonInfo);
        tempMapTotalRoute.putAll(tempMapOfRoutes);
        totalListWagon.addAll(copyListOfWagon);

        logger.debug("mapFinalWagonInfo: {}", mapFinalWagonInfo);
        logger.info("Stop root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");
    }

    private boolean isCheckMore40(Wagon wagon, Route route) {
        int sum = 0;
        try {
            if (getFullMonthCircleOfWagonImpl.getListOfDaysOfWagon(wagon.getNumberOfWagon()).isEmpty()) {
                return false;
            } else {
                for (Integer list : getFullMonthCircleOfWagonImpl.getListOfDaysOfWagon(wagon.getNumberOfWagon())) {
                    sum += list;
                }
                sum += Math.ceil(Integer.parseInt(route.getDistanceOfWay()) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(route.getDistanceOfWay())));
                sum += LOADING_WAGON;
                return sum >= MAX_COUNT_DAYS_DECADE ? false : true;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void fillFinalMapByOrders() {
        mapFinalOrderInfo.clear();
        for (Map.Entry<Integer, Route> _map : getListOfDistance.getMapOfRoutes().entrySet()) {
            for (Map.Entry<Integer, Route> _tempMap : tempMapTotalRoute.entrySet()) {
                List<Integer> list = new ArrayList<>();
                if (_map.getValue().getNumberOrder().equals(_tempMap.getValue().getNumberOrder())) {
                    list.add(_tempMap.getValue().getCountOrders());
                    list.add(_map.getValue().getCountOrders() - _tempMap.getValue().getCountOrders());
                    mapFinalOrderInfo.put(_map.getValue(), list);
                }
            }
        }
    }

    private void putRateAndTariff(Map<String, WagonFinalInfo> map) {
        Map<String, WagonFinalInfo> tempResultMap = new HashMap<>();
        Map<Integer, RateClass> tempMapRates = new HashMap<>(getListOfRates.getMapOfRates());
        Map<Integer, EmptyRoute> tempMapEmptyRoutes = new HashMap<>(getListOfEmptyRoutes.getMapOfEmptyRoutes());

        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
            // Подбираем ставку из файле
            for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                List<RateClass> tempListSelectedRates = new ArrayList<>();
                for (Map.Entry<Integer, RateClass> _tempMapRates : tempMapRates.entrySet()) {
                    if (_map.getValue().getListRouteInfo().get(i).getRoute().getCustomer().equals(_tempMapRates.getValue().getCustomer()) &&
                            _map.getValue().getListRouteInfo().get(i).getRoute().getNameOfStationDeparture().equals(_tempMapRates.getValue().getNameOfStationDeparture()) &&
                            _map.getValue().getListRouteInfo().get(i).getRoute().getNameOfStationDestination().equals(_tempMapRates.getValue().getNameOfStationDestination()) &&
                            _map.getValue().getListRouteInfo().get(i).getRoute().getCargo().getCargoType() == _tempMapRates.getValue().getCargo().getCargoType()) {
                        tempListSelectedRates.add(_tempMapRates.getValue());
                    }
                }

                Collections.sort(tempListSelectedRates);
                if (!tempListSelectedRates.isEmpty())
                    _map.getValue().getListRouteInfo().get(i).setRate(tempListSelectedRates.get(0).getRate());

                // Подбираем тариф из файле
                for (Map.Entry<Integer, EmptyRoute> _tempMapEmptyRoutes : tempMapEmptyRoutes.entrySet()) {
                    if (_map.getValue().getListRouteInfo().get(i).getCurrentNameOfStationOfWagon().equals(_tempMapEmptyRoutes.getValue().getNameOfStationDeparture()) &&
                            _map.getValue().getListRouteInfo().get(i).getRoute().getNameOfStationDeparture().equals(_tempMapEmptyRoutes.getValue().getNameOfStationDestination()) &&
                            _map.getValue().getListRouteInfo().get(i).getCargoType() == _tempMapEmptyRoutes.getValue().getCargo().getCargoType()) {
                        _map.getValue().getListRouteInfo().get(i).setTariff(_tempMapEmptyRoutes.getValue().getTariff());
                    }
                }
            }
            tempResultMap.put(_map.getKey(), _map.getValue());
        }
        mapFinalWagonInfo.putAll(tempResultMap);
        lookingForRateAndTariffInDB(mapFinalWagonInfo);
    }

    private void lookingForRateAndTariffInDB(Map<String, WagonFinalInfo> map) {
        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {

            // Не нашли тариф в файле, поищем в базе
            for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                if (_map.getValue().getListRouteInfo().get(i).getTariff() == null) {
                    Object tariff = getTariff.getRateOrTariff(_map.getValue().getListRouteInfo().get(i).getCurrentKeyOfStationOfWagon(),
                            _map.getValue().getListRouteInfo().get(i).getRoute().getKeyOfStationDeparture(),
                            _map.getValue().getListRouteInfo().get(i).getCargoType());
                    if (tariff != null) {
                        _map.getValue().getListRouteInfo().get(i).setTariff(tariff);
                        _map.getValue().getListRouteInfo().get(i).setLoadingTariffFromDB(Boolean.TRUE);
                        if (!basicClass.isFlag()) {
                            basicClass.setFlag(Boolean.TRUE);
                        }
                    }
                }

                // Не нашли ставку в файле, поищем в базе
                if (_map.getValue().getListRouteInfo().get(i).getRate() == null) {
                    Object rate = getRate.getRateOrTariff(_map.getValue().getListRouteInfo().get(i).getRoute().getKeyOfStationDeparture(),
                            _map.getValue().getListRouteInfo().get(i).getRoute().getKeyOfStationDestination(),
                            _map.getValue().getListRouteInfo().get(i).getRoute().getCargo().getCargoType());
                    if (rate != null) {
                        _map.getValue().getListRouteInfo().get(i).setRate(rate);
                        _map.getValue().getListRouteInfo().get(i).setLoadingRateFromDB(Boolean.TRUE);
                        if (!basicClass.isFlag()) {
                            basicClass.setFlag(Boolean.TRUE);
                        }
                    }
                }
            }
        }
    }

    private void checkEmptyTariffOrRate(Map<String, WagonFinalInfo> map) {
        for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
            // Индекс последнего маршрута
            for (int i = 0; i < _map.getValue().getListRouteInfo().size(); i++) {
                if (_map.getValue().getListRouteInfo().get(i).getRate() == null || _map.getValue().getListRouteInfo().get(i).getTariff() == null) {
                    _map.getValue().getListRouteInfo().get(i).setEmpty(Boolean.TRUE);
                    if (!basicClass.isFlag()) {
                        basicClass.setFlag(Boolean.TRUE);
                    }
                }
            }
        }
    }

    public BasicClassImpl getBasicClass() {
        return basicClass;
    }

    public void setBasicClass(BasicClassImpl basicClass) {
        this.basicClass = basicClass;
    }

    public GetListOfDistanceImpl getGetListOfDistance() {
        return getListOfDistance;
    }

    public void setGetListOfDistance(GetListOfDistanceImpl getListOfDistance) {
        this.getListOfDistance = getListOfDistance;
    }

    public Map<String, WagonFinalInfo> getMapFinalWagonInfo() {
        return mapFinalWagonInfo;
    }

    public void setMapFinalWagonInfo(Map<String, WagonFinalInfo> mapFinalWagonInfo) {
        this.mapFinalWagonInfo = mapFinalWagonInfo;
    }

    public Map<Route, List<Integer>> getMapFinalOrderInfo() {
        return mapFinalOrderInfo;
    }

    public void setMapFinalOrderInfo(Map<Route, List<Integer>> mapFinalOrderInfo) {
        this.mapFinalOrderInfo = mapFinalOrderInfo;
    }

    public List<Wagon> getTotalListWagon() {
        return totalListWagon;
    }

    public void setTotalListWagon(List<Wagon> totalListWagon) {
        this.totalListWagon = totalListWagon;
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
}
