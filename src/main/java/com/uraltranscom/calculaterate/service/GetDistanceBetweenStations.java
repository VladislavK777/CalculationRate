package com.uraltranscom.calculaterate.service;

/**
 *
 * Интерфейс получения расстояния между станциями
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

import java.sql.SQLException;
import java.util.List;

public interface GetDistanceBetweenStations {
    List<Object> getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination, String keyCargo) throws SQLException;

}
