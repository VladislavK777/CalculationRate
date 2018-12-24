package com.uraltranscom.calculaterate.service;

/**
 *
 * Интерфейс добавления в БД ставки или тарифа
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 * 13.10.2018
 *   1. Версия 2.0
 *
 */

public interface InsertRate {
    void insertRate(String keyOfStationDeparture, String keyOfStationDestination, int cargoType, double rate);
}
