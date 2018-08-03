package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.Wagon;
import com.uraltranscom.dynamicdistributionpark.service.GetList;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import com.uraltranscom.dynamicdistributionpark.util.PropertyUtil;
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
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

@Service
@Component
public class GetListOfDistanceImpl extends JavaHelperBase implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfDistanceImpl.class);

    @Autowired
    private GetListOfRoutesImpl getListOfRoutesImpl;
    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetListOfWagonsImpl getListOfWagonsImpl;
    @Autowired
    private CheckExistKeyOfStationImpl checkExistKeyOfStationImpl;
    @Autowired
    private ClassHandlerLookingForImpl classHandlerLookingFor;
    @Autowired
    private PropertyUtil propertyUtil;

    // Основная мапа
    private Map<String, List<Integer>> rootMapWithDistances = new HashMap<>();

    private Map<Integer, Route> mapOfRoutes = new HashMap<>();
    private List<Wagon> listOfWagons = new ArrayList<>();

    @Override
    public void fillMap() {
        logger.info("Start process fill map with distances");
        rootMapWithDistances = deSerializeMap();
        mapOfRoutes = new HashMap<>(getListOfRoutesImpl.getMapOfRoutes());
        listOfWagons = new ArrayList<>(getListOfWagonsImpl.getListOfWagons());

        Iterator<Map.Entry<Integer, Route>> iterator = mapOfRoutes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Route> entry = iterator.next();
            for (int i = 0; i < listOfWagons.size(); i++) {
                int index = listOfWagons.get(i).getListRoutes().size() - 1;

                String wagonKeyOfStationDestination = listOfWagons.get(i).getListRoutes().get(index).getKeyOfStationDestination();
                String routeKeyOfStationDeparture = entry.getValue().getKeyOfStationDeparture();

                String key = wagonKeyOfStationDestination + "_" + routeKeyOfStationDeparture;

                // Заполняем мапы расстояний
                if (!rootMapWithDistances.containsKey(key)) {
                    List<Integer> listDistance = getDistanceBetweenStations.getDistanceBetweenStations(wagonKeyOfStationDestination, routeKeyOfStationDeparture);
                    int distance = listDistance.get(0);
                    if (distance == -1) {
                        if (!checkExistKeyOfStationImpl.checkExistKey(routeKeyOfStationDeparture)) {
                            classHandlerLookingFor.getBasicClass().getSetOfError().add("Проверьте код станции " + routeKeyOfStationDeparture + " в файле заявок");
                            logger.error("Проверьте код станции " + routeKeyOfStationDeparture + " в файле заявок");
                            iterator.remove();
                            break;
                        }
                        if (!checkExistKeyOfStationImpl.checkExistKey(wagonKeyOfStationDestination)) {
                            classHandlerLookingFor.getBasicClass().getSetOfError().add("Проверьте код станции " + wagonKeyOfStationDestination + " в файле дислокации вагонов");
                            logger.error("Проверьте код станции {}", wagonKeyOfStationDestination + " в файле дислокации вагонов");
                            listOfWagons.remove(i);
                            break;
                        }
                        if (checkExistKeyOfStationImpl.checkExistKey(routeKeyOfStationDeparture) && checkExistKeyOfStationImpl.checkExistKey(wagonKeyOfStationDestination)) {
                            classHandlerLookingFor.getBasicClass().getSetOfError().add("Не нашел расстояние между " + wagonKeyOfStationDestination + " и " + routeKeyOfStationDeparture);
                            logger.error("Не нашел расстояние между " + wagonKeyOfStationDestination + " и " + routeKeyOfStationDeparture);
                            iterator.remove();
                            listOfWagons.remove(i);
                            break;
                        }
                    } else {
                        rootMapWithDistances.put(key, listDistance);
                    }
                }
            }
        }
        serializeMap((HashMap<String, List<Integer>>) rootMapWithDistances);
        logger.info("Stop process fill map with distances");
    }

    private void serializeMap(HashMap<String, List<Integer>> map) {
        File file = new File(propertyUtil.getProperty("pathsavefile"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.info("IO Exception", e.getMessage());
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream))
        {
            objectOutputStream.writeObject(map);
        } catch (Exception e) {
            logger.info("IO Exception", e.getMessage());
        }
    }
    private Map<String, List<Integer>> deSerializeMap() {
        File file = new File(propertyUtil.getProperty("pathsavefile"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.info("IO Exception", e.getMessage());
            }
        }
        Map<String, List<Integer>> map = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream (file);
             ObjectInputStream objectInputStream = new ObjectInputStream (fileInputStream))
        {
            map = (Map<String, List<Integer>>) objectInputStream.readObject();
            logger.info("Deserialization success");
        } catch (Exception e) {
            logger.info("IO Exception", e.getMessage());
        }
        return map;
    }

    //TODO Улучшить
    public List<Integer> listDistance (String keyOfStationDeparture, String keyOfStationDestination) {
        String key = keyOfStationDeparture + "_" + keyOfStationDestination;
        List<Integer> listDistance;
        listDistance = getDistanceBetweenStations.getDistanceBetweenStations(keyOfStationDeparture, keyOfStationDestination);
        int distance = listDistance.get(0);
        if (distance == -1) {
            if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationDestination)) {
                classHandlerLookingFor.getBasicClass().getListOfError().add("Проверьте код станции " + keyOfStationDestination + " в файле заявок");
                logger.error("Проверьте код станции " + keyOfStationDestination + " в файле заявок");
                return null;
            }
            if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationDeparture)) {
                classHandlerLookingFor.getBasicClass().getListOfError().add("Проверьте код станции " + keyOfStationDeparture + " в файле дислокации вагонов");
                logger.error("Проверьте код станции {}", keyOfStationDeparture + " в файле дислокации вагонов");
                return null;
            }
            if (checkExistKeyOfStationImpl.checkExistKey(keyOfStationDestination) && checkExistKeyOfStationImpl.checkExistKey(keyOfStationDeparture)) {
                classHandlerLookingFor.getBasicClass().getListOfError().add("Не нашел расстояние между " + keyOfStationDeparture + " и " + keyOfStationDestination);
                logger.error("Не нашел расстояние между " + keyOfStationDeparture + " и " + keyOfStationDestination);
                return null;
            }
        } else {
            rootMapWithDistances.put(key, listDistance);
            serializeMap((HashMap<String, List<Integer>>) rootMapWithDistances);
            return listDistance;
        }
        return null;
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
