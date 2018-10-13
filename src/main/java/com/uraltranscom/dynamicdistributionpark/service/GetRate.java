package com.uraltranscom.dynamicdistributionpark.service;

/**
 *
 * Интерфейс получения ставки
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 *
 */

public interface GetRate {
    Object getRate(String keyOfStationDeparture, String keyOfStationDestination, int cargoType);
}
