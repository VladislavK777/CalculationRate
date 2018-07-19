package com.uraltranscom.dynamicdistributionpark.service;

/**
 *
 * Интерфейс получения расстояния между станциями
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

import java.sql.SQLException;
import java.util.List;

public interface GetDistanceBetweenStations {
    List<Integer> getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination) throws SQLException;

}
