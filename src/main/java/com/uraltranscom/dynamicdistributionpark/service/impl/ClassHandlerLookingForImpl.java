package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.EmptyRoute;
import com.uraltranscom.dynamicdistributionpark.model.RateClass;
import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.Wagon;
import com.uraltranscom.dynamicdistributionpark.model_ext.WagonFinalInfo;
import com.uraltranscom.dynamicdistributionpark.service.ClassHandlerLookingFor;
import com.uraltranscom.dynamicdistributionpark.service.additional.CompareMapValue;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
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
    private BasicClassLookingForImpl basicClassLookingFor;
    @Autowired
    private GetRateImpl getRate;
    @Autowired
    private GetTariffImpl getTariff;

    private Map<String, WagonFinalInfo> mapFinalWagonInfo = new HashMap<>();

    private ClassHandlerLookingForImpl() {
    }

    @Override
    public void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> tempListOfWagons) {
        logger.info("Start root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");

        // Заполняем мапы
        List<Wagon> copyListOfWagon = new ArrayList<>(tempListOfWagons);
        Map<Integer, Route> tempMapOfRoutes = new HashMap<>(mapOfRoutes);

        Map<String, WagonFinalInfo> tempMapWagonInfo = new HashMap<>();

        // Мапа расстояний
        Map<List<Object>, Integer> mapDistance = new HashMap<>();

        // Запускаем цикл
        Boolean isOk = true;
        while (isOk) {
            isOk = false;
            // Очищаем массивы
            mapDistance.clear();

            for (Wagon _copyListOfWagon : copyListOfWagon) {
                // Получаем код станции назначения вагона
                String keyOfStationOfWagonDestination = _copyListOfWagon.getKeyOfStationDestination().trim();

                // По каждому вагону высчитываем расстояние до каждой начальной станнции маршрутов
                // Цикл расчета расстояния и заполнения мапы
                for (Map.Entry<Integer, Route> _tempMapOfRoutes : tempMapOfRoutes.entrySet()) {

                    List<Object> list = new ArrayList<>();
                    String keyOfStationDeparture = _tempMapOfRoutes.getValue().getKeyOfStationDeparture();
                    list.add(_copyListOfWagon);
                    list.add(_tempMapOfRoutes.getValue());
                    String key = keyOfStationOfWagonDestination + "_" + keyOfStationDeparture;

                    // Ищем в готовой мапе расстояние
                    if (getListOfDistance.getRootMapWithDistances().containsKey(key)) {
                        if (getListOfDistance.getRootMapWithDistances().get(key).get(1) == 0) {
                            switch (_copyListOfWagon.getCargo().getCargoType()) {
                                case 3:
                                    if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 300) {
                                        mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                    }
                                    break;
                                default:
                                    if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 600) {
                                        mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                    }
                                    break;
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
                }
            }

            // Сортируем мапу по значению
            Map<List<Object>, Integer> mapDistanceSort = new LinkedHashMap<>();

            int index = mapDistance.size();
            CompareMapValue[] compareMapValues = new CompareMapValue[index];
            index = 0;
            for (Map.Entry<List<Object>, Integer> entry : mapDistance.entrySet()) {
                compareMapValues[index++] = new CompareMapValue(entry.getKey(), entry.getValue());
            }
            Arrays.sort(compareMapValues);
            for (CompareMapValue cmv : compareMapValues) {
                mapDistanceSort.put(cmv.wagon, cmv.distance);
            }

            // Цикл формирования рейсов
            // Проверяем на пустоту мап, либо вагоны, либо рейсы
            outer:
            if (!mapDistanceSort.isEmpty() && !copyListOfWagon.isEmpty()) {
                for (Map.Entry<List<Object>, Integer> mapDistanceSortFirstElement : mapDistanceSort.entrySet()) {
                    List<Object> listRouteMinDistance = mapDistanceSortFirstElement.getKey();
                    Route route = (Route) listRouteMinDistance.get(1);
                    Wagon wagon = (Wagon) listRouteMinDistance.get(0);
                    String nameOfStationDepartureOfWagon = route.getNameOfStationDeparture();
                    String keyOfStationDepartureOfWagon = route.getKeyOfStationDeparture();

                    final int[] o = {0};
                    Map<Integer, Route> tempMapOfRouteForDelete = new HashMap<>();
                    tempMapOfRoutes.forEach((k, v) -> {
                        tempMapOfRouteForDelete.put(o[0], v);
                        o[0]++;
                    });

                    Iterator<Map.Entry<Integer, Route>> it = tempMapOfRoutes.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Integer, Route> entry = it.next();
                        for (int j = 0; j < tempMapOfRouteForDelete.size(); j++) {
                            // Находим маршрут для вагона
                            if (tempMapOfRouteForDelete.get(j).equals(route) && entry.getValue().getCountOrders() > 0) {
                                if (tempMapOfRouteForDelete.get(j).equals(entry.getValue())) {
                                    if (wagon.getVolume() >= entry.getValue().getVolumePeriod().getVolumeFrom() && wagon.getVolume() <= entry.getValue().getVolumePeriod().getVolumeTo()) {

                                        int getKeyNumber = 0;

                                        for (int i = 0; i < copyListOfWagon.size(); i++) {
                                            if (copyListOfWagon.get(i).getNumberOfWagon().equals(wagon.getNumberOfWagon())) {
                                                getKeyNumber = i;
                                            }
                                        }

                                        // Число дней пройденных вагоном
                                        int countCircleDays = getFullMonthCircleOfWagonImpl.fullDays(mapDistanceSortFirstElement.getValue(), route.getDistanceOfWay());

                                        // Удаляем вагон
                                        /*for (int i = 0; i < tempListOfWagons.size(); i++) {
                                            if (tempListOfWagons.get(i).getNumberOfWagon().equals(copyListOfWagon.get(getKeyNumber).getNumberOfWagon())) {
                                                tempListOfWagons.remove(i);
                                            }
                                        }*/

                                        copyListOfWagon.remove(getKeyNumber);

                                        // Уменьшаем количество рейсов у маршрута
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

                                        // Удаляем маршрут, если по нему 0 рейсов
                                        if (tempMapOfRoutes.get(entry.getKey()).getCountOrders() == 0) {
                                            it.remove();
                                        }

                                        tempMapWagonInfo.put(wagon.getNumberOfWagon(),
                                                new WagonFinalInfo(
                                                        wagon.getNumberOfWagon(),
                                                        countCircleDays,
                                                        mapDistanceSortFirstElement.getValue(),
                                                        wagon.getNameOfStationDestination(),
                                                        wagon.getKeyOfStationDestination(),
                                                        nameOfStationDepartureOfWagon,
                                                        keyOfStationDepartureOfWagon,
                                                        entry.getValue(),
                                                        wagon.getCargo(),
                                                        wagon.getCargo().getCargoType()
                                                )
                                        );
                                        //basicClassLookingFor.getListOfDistributedRoutesAndWagons().add(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue(), nameOfStationDepartureOfWagon, tempMapOfRouteForDelete.get(j).getNameOfStationDeparture() + " - " + tempMapOfRouteForDelete.get(j).getNameOfStationDestination(), wagon.getCargo().trim(), getListOfDistance.getRootMapWithTypeOfCargo().get(wagon.getKeyItemCargo())));
                                        //basicClassLookingFor.getTotalMapWithWagonNumberAndRoute().put(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue()), tempMapOfRouteForDelete.get(j));

                                        isOk = true;
                                        // Выходим из цикла, так как с ним больше ничего не сделать
                                        break outer;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        putRateAndTariff(tempMapWagonInfo);
        checkEmptyTariffOrRate(mapFinalWagonInfo);
        logger.debug("mapFinalWagonInfo: {}", mapFinalWagonInfo);

        logger.info("Stop root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");
    }

    private void putRateAndTariff(Map<String, WagonFinalInfo> map) {
        Map<String, WagonFinalInfo> tempResultMap = new HashMap<>();
        Map<Integer, RateClass> tempMapRates = new HashMap<>(basicClassLookingFor.getGetListOfRates().getMapOfRates());
        Map<Integer, EmptyRoute> tempMapEmptyRoutes = new HashMap<>(basicClassLookingFor.getGetListOfEmptyRoutes().getMapOfEmptyRoutes());

        for (Map.Entry<String, WagonFinalInfo> _map: map.entrySet()) {
            List<RateClass> tempListSelectedRates = new ArrayList<>();

            // Подбираем ставку из файле
            for (Map.Entry<Integer, RateClass> _tempMapRates: tempMapRates.entrySet()) {
                if (_map.getValue().getRoute().getCustomer().equals(_tempMapRates.getValue().getCustomer()) &&
                        _map.getValue().getRoute().getNameOfStationDeparture().equals(_tempMapRates.getValue().getNameOfStationDeparture()) &&
                        _map.getValue().getRoute().getNameOfStationDestination().equals(_tempMapRates.getValue().getNameOfStationDestination()) &&
                        _map.getValue().getRoute().getCargo().getCargoType() == _tempMapRates.getValue().getCargo().getCargoType()) {
                    tempListSelectedRates.add(_tempMapRates.getValue());
                }
            }
            Collections.sort(tempListSelectedRates);
            if (!tempListSelectedRates.isEmpty()) _map.getValue().setRate(tempListSelectedRates.get(0).getRate());

            // Подбираем тариф из файле
            for (Map.Entry<Integer, EmptyRoute> _tempMapEmptyRoutes: tempMapEmptyRoutes.entrySet()) {
                if (_map.getValue().getCurrentNameOfStationOfWagon().equals(_tempMapEmptyRoutes.getValue().getNameOfStationDeparture()) &&
                        _map.getValue().getRoute().getNameOfStationDeparture().equals(_tempMapEmptyRoutes.getValue().getNameOfStationDestination()) &&
                        _map.getValue().getCargoType() == _tempMapEmptyRoutes.getValue().getCargo().getCargoType()) {
                    _map.getValue().setTariff(_tempMapEmptyRoutes.getValue().getTariff());
                }
            }
            tempResultMap.put(_map.getKey(), _map.getValue());
        }
        mapFinalWagonInfo.putAll(tempResultMap);
        lookingForRateAndTariffInDB(mapFinalWagonInfo);
    }

    private void lookingForRateAndTariffInDB(Map<String, WagonFinalInfo> map) {
        for (Map.Entry<String, WagonFinalInfo> _map: map.entrySet()) {
            // Не нашли тариф в файле, поищем в базе
            if (_map.getValue().getTariff() == null) {
                Object tariff = getTariff.getRateOrTariff(_map.getValue().getCurrentKeyOfStationOfWagon(),
                        _map.getValue().getRoute().getKeyOfStationDeparture(),
                        _map.getValue().getCargoType());
                if (tariff != null) {
                    _map.getValue().setTariff(tariff);
                    _map.getValue().setLoadingTariffFromDB(Boolean.TRUE);
                }
            }
            // Не нашли ставку в файле, поищем в базе
            if (_map.getValue().getRate() == null) {
                Object rate = getRate.getRateOrTariff(_map.getValue().getRoute().getKeyOfStationDeparture(),
                        _map.getValue().getRoute().getKeyOfStationDestination(),
                        _map.getValue().getRoute().getCargo().getCargoType());
                if (rate != null) {
                    System.out.println(rate);
                    _map.getValue().setRate(rate);
                    System.out.println(_map.getValue().getRate());
                    _map.getValue().setLoadingRateFromDB(Boolean.TRUE);
                }
            }
        }
    }

    private void checkEmptyTariffOrRate(Map<String, WagonFinalInfo> map) {
        for (Map.Entry<String, WagonFinalInfo> _map: map.entrySet()) {
            if (_map.getValue().getRate() == null || _map.getValue().getTariff() == null) {
                _map.getValue().setEmpty(Boolean.TRUE);
                if (!basicClassLookingFor.isFlag()) {
                    basicClassLookingFor.setFlag(Boolean.TRUE);
                }
            }
        }
    }

    public Map<String, WagonFinalInfo> getMapFinalWagonInfo() {
        return mapFinalWagonInfo;
    }

    public void setMapFinalWagonInfo(Map<String, WagonFinalInfo> mapFinalWagonInfo) {
        this.mapFinalWagonInfo = mapFinalWagonInfo;
    }
}
