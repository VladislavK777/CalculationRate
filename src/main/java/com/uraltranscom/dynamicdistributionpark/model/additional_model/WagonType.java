package com.uraltranscom.dynamicdistributionpark.model.additional_model;

/**
 *
 * Класс типа вагона
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class WagonType {
    private String wagonType;

    public WagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    public String getWagonType() {
        return wagonType;
    }

    public void setWagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    @Override
    public String toString() {
        return wagonType;
    }
}
