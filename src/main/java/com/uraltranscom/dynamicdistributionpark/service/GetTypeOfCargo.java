package com.uraltranscom.dynamicdistributionpark.service;

/**
 *
 * Интерфейс получения класса груза
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public interface GetTypeOfCargo {
    /**
     *
     * @param key - код груза или название груза
     * @return Возвращает класс груза
     */
    int getTypeOfCargo(String key);
}
