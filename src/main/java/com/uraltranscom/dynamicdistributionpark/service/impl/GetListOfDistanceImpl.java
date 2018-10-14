package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.Wagon;
import com.uraltranscom.dynamicdistributionpark.service.GetList;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 *
 * Класс получения списка первоначальных расстояний
 * Implementation for {@link GetList} interface
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 * 13.10.2018
 *   1. Версия 2.0
 *
 */

@Service
@Component
public class GetListOfDistanceImpl implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfDistanceImpl.class);

    @Autowired
    private GetListOfRoutesImpl getListOfRoutesImpl;
    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetListOfWagonsImpl getListOfWagonsImpl;
    //@Autowired
    //private CheckExistKeyOfStationImpl checkExistKeyOfStationImpl;
    @Autowired
    private ClassHandlerLookingForImpl classHandlerLookingFor;

    // Основная мапа
    private Map<String, List<Integer>> rootMapWithDistances;

    private Map<Integer, Route> mapOfRoutes;
    private List<Wagon> listOfWagons;

    @Override
    public void fillMap() {
        logger.info("Start process fill map with distances");
        rootMapWithDistances = deSerializeMap();
        mapOfRoutes = new HashMap<>(getListOfRoutesImpl.getMapOfRoutes());
        listOfWagons = new ArrayList<>(getListOfWagonsImpl.getListOfWagons());
        logger.info("Stop process fill map with distances");
    }

    void serializeMap(HashMap<String, List<Integer>> map) {
        File file = new File(JavaHelperBase.PATH_SAVE_FILE_MAP);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.info("Файл не сериализации найден и был создан:", e.getMessage());
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream))
        {
            objectOutputStream.writeObject(map);
            logger.info("Карты успешно сохранены");
        } catch (Exception e) {
            logger.error("IO Exception", e.getMessage());
        }
    }

    private Map<String, List<Integer>> deSerializeMap() {
        File file = new File(JavaHelperBase.PATH_SAVE_FILE_MAP);
        if (!file.exists()) {
            logger.info("Файл сериализации не найден");
            return new HashMap<>();
        }
        Map<String, List<Integer>> map = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream (file);
             ObjectInputStream objectInputStream = new ObjectInputStream (fileInputStream))
        {
            map = (Map<String, List<Integer>>) objectInputStream.readObject();
            logger.info("Карты успешно загружены");
        } catch (Exception e) {
            logger.error("IO Exception", e.getMessage());
        }
        return map;
    }

    //TODO Улучшить
    public List<Integer> listDistance (String keyOfStationDeparture, String keyOfStationDestination, String keyCargo) {
        String key = keyOfStationDeparture + "_" + keyOfStationDestination + "_" + keyCargo;
        List<Integer> listDistance = new ArrayList<>();
        if (keyCargo.equals("000000")) {
            listDistance.add(0);
            listDistance.add(0);
            listDistance.add(0);
        } else {
            listDistance = getDistanceBetweenStations.getDistanceBetweenStations(keyOfStationDeparture, keyOfStationDestination, keyCargo);
            int distance = listDistance.get(0);
            if (distance == -20000) {
                classHandlerLookingFor.getBasicClass().getListOfError().add(String.format("Не нашел расстояние между %s и %s", keyOfStationDeparture, keyOfStationDestination));
                logger.error(String.format("Не нашел расстояние между %s и %s", keyOfStationDeparture, keyOfStationDestination));
                return null;
            }
        }
        rootMapWithDistances.put(key, listDistance);
        serializeMap((HashMap<String, List<Integer>>) rootMapWithDistances);
        return listDistance;
    }

    public void fillRootMapWithDistances(List<Wagon> listWagon, Map<Integer, Route> mapRoutes) {
        //logger.info("Start method fillRootMapWithDistances");
        Iterator<Map.Entry<Integer, Route>> iterator = mapRoutes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Route> entry = iterator.next();
            for (int i = 0; i < listWagon.size(); i++) {
                int index = listWagon.get(i).getListRoutes().size() - 1;

                String wagonKeyOfStationDestination = listWagon.get(i).getListRoutes().get(index).getKeyOfStationDestination();
                String routeKeyOfStationDeparture = entry.getValue().getKeyOfStationDeparture();
                String keyCargo = listWagon.get(i).getListRoutes().get(index).getCargo().getKeyCargo();
                String key = wagonKeyOfStationDestination + "_" + routeKeyOfStationDeparture + "_" + keyCargo;

                if (!wagonKeyOfStationDestination.equals("")) {
                    if (keyCargo.equals("000000")) {
                        List<Integer> listDistance = new ArrayList<>();
                        listDistance.add(0);
                        listDistance.add(0);
                        listDistance.add(0);
                        rootMapWithDistances.put(key, listDistance);
                    } else {
                        // Заполняем мапы расстояний
                        if (!rootMapWithDistances.containsKey(key)) {
                            List<Integer> listDistance = getDistanceBetweenStations.getDistanceBetweenStations(wagonKeyOfStationDestination, routeKeyOfStationDeparture, keyCargo);
                            int distance = listDistance.get(0);
                            if (distance == -20000) {
                                classHandlerLookingFor.getBasicClass().getSetOfError().add(String.format("Не нашел расстояние между %s и %s", wagonKeyOfStationDestination, routeKeyOfStationDeparture));
                                logger.error(String.format("Не нашел расстояние между %s и %s", wagonKeyOfStationDestination, routeKeyOfStationDeparture));
                                iterator.remove();
                                listWagon.remove(i);
                                break;
                            } else {
                                rootMapWithDistances.put(key, listDistance);
                            }
                        }
                    }
                }
            }
        }
        //serializeMap((HashMap<String, List<Integer>>) rootMapWithDistances);
    }

    public Map<String, List<Integer>> getRootMapWithDistances() {
        return rootMapWithDistances;
    }

    public void setRootMapWithDistances(Map<String, List<Integer>> rootMapWithDistances) {
        this.rootMapWithDistances = rootMapWithDistances;
    }

    public GetListOfRoutesImpl getGetListOfRoutesImpl() {
        return getListOfRoutesImpl;
    }

    public void setGetListOfRoutesImpl(GetListOfRoutesImpl getListOfRoutesImpl) {
        this.getListOfRoutesImpl = getListOfRoutesImpl;
    }

    public GetListOfWagonsImpl getGetListOfWagonsImpl() {
        return getListOfWagonsImpl;
    }

    public void setGetListOfWagonsImpl(GetListOfWagonsImpl getListOfWagonsImpl) {
        this.getListOfWagonsImpl = getListOfWagonsImpl;
    }

    public Map<Integer, Route> getMapOfRoutes() {
        return mapOfRoutes;
    }

    public void setMapOfRoutes(Map<Integer, Route> mapOfRoutes) {
        this.mapOfRoutes = mapOfRoutes;
    }

    public List<Wagon> getListOfWagons() {
        return listOfWagons;
    }

    public void setListOfWagons(List<Wagon> listOfWagons) {
        this.listOfWagons = listOfWagons;
    }
}
