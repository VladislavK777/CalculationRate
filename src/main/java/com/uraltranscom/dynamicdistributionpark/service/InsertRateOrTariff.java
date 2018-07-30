package com.uraltranscom.dynamicdistributionpark.service;

/**
 *
 * Интерфейс добавления в БД ставки или тарифа
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 *
 */

public interface InsertRateOrTariff {
    void insertRateOfTariff(String keyOfStationDeparture, String keyOfStationDestination, int cargoType, double rateOrTariff);
}
