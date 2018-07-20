package com.uraltranscom.dynamicdistributionpark.service;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.Wagon;

import java.util.List;
import java.util.Map;

/**
 *
 * Интерфейс класса-обработчика основного алгоритма поиска
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public interface ClassHandlerLookingFor {
    void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> listOfWagons);
}
