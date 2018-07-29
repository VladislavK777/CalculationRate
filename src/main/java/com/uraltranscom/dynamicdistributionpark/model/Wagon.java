package com.uraltranscom.dynamicdistributionpark.model;

import com.uraltranscom.dynamicdistributionpark.model.additional_model.WagonType;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 *
 * Класс Вагон
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class Wagon extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Wagon.class);

    private String numberOfWagon; // Номер вагона
    private WagonType wagonType; // Тип вагона
    private List<Route> listRoutes; // Список рейсов
    private int volume; // Объем вагона

    public Wagon(String numberOfWagon, List<Route> listRoutes, int volume) {
        this.wagonType = new WagonType(TYPE_OF_WAGON_KR);
        this.numberOfWagon = numberOfWagon;
        this.listRoutes = listRoutes;
        this.volume = volume;
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
    }

    public WagonType getWagonType() {
        return wagonType;
    }

    public void setWagonType(WagonType wagonType) {
        this.wagonType = wagonType;
    }

    public List<Route> getListRoutes() {
        return listRoutes;
    }

    public void setListRoutes(List<Route> listRoutes) {
        this.listRoutes = listRoutes;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wagon wagon = (Wagon) o;
        return volume == wagon.volume &&
                Objects.equals(numberOfWagon, wagon.numberOfWagon) &&
                Objects.equals(wagonType, wagon.wagonType) &&
                Objects.equals(listRoutes, wagon.listRoutes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, wagonType, listRoutes, volume);
    }

    @Override
    public String toString() {
        return "Wagon{" +
                "numberOfWagon='" + numberOfWagon + '\'' +
                ", wagonType=" + wagonType +
                ", listRoutes=" + listRoutes +
                ", volume=" + volume +
                '}';
    }
}
