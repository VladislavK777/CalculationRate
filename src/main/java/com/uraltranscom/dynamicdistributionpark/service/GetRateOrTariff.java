package com.uraltranscom.dynamicdistributionpark.service;

/**
 *
 * Интерфейс получения ставки или тирифв
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 *
 */

public interface GetRateOrTariff {
    Object getRateOrTariff(String keyOfStationDeparture, String keyOfStationDestination, int cargoType);
}
