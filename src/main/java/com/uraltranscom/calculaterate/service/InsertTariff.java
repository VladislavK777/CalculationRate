package com.uraltranscom.calculaterate.service;

/**
 *
 * Интерфейс добавления в БД  тарифа
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 26.07.2018
 *
 * 13.10.2018
 *   1. Версия 2.0
 *
 */

public interface InsertTariff {
    void insertTariff(String keyOfStationDeparture, String keyOfStationDestination, String keyCargo, double tariff);
}
