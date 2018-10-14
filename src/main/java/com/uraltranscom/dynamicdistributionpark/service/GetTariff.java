package com.uraltranscom.dynamicdistributionpark.service;

import java.util.List;

/**
 *
 * Интерфейс получения тирифв
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 12.10.2018
 *
 * 12.10.2018
 *   1. Версия 2.0
 *
 */

public interface GetTariff {
    List<Double> getTariff(String keyOfStationDeparture, String keyOfStationDestination, int distanceStart, int distanceEnd, int distance, String keyCargo);
}
