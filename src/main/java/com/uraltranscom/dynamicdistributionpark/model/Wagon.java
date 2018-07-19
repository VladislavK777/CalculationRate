package com.uraltranscom.dynamicdistributionpark.model;

import com.uraltranscom.dynamicdistributionpark.model.additional_model.CargoClass;
import com.uraltranscom.dynamicdistributionpark.model.additional_model.WagonType;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Название станции назначения
    private int volume; // Объем вагона
    private CargoClass cargo; // Груз

    public Wagon(String numberOfWagon, String keyOfStationDestination, String nameOfStationDestination, int volume, String nameCargo, String keyCargo) {
        this.numberOfWagon = numberOfWagon;
        this.wagonType = new WagonType(TYPE_OF_WAGON_KR);
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.volume = volume;
        this.cargo = new CargoClass(nameCargo, keyCargo);
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

    public String getKeyOfStationDestination() {
        return keyOfStationDestination;
    }

    public void setKeyOfStationDestination(String keyOfStationDestination) {
        this.keyOfStationDestination = keyOfStationDestination;
    }

    public String getNameOfStationDestination() {
        return nameOfStationDestination;
    }

    public void setNameOfStationDestination(String nameOfStationDestination) {
        this.nameOfStationDestination = nameOfStationDestination;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public CargoClass getCargo() {
        return cargo;
    }

    public void setCargo(CargoClass cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wagon wagon = (Wagon) o;
        return volume == wagon.volume &&
                Objects.equals(numberOfWagon, wagon.numberOfWagon) &&
                Objects.equals(wagonType, wagon.wagonType) &&
                Objects.equals(keyOfStationDestination, wagon.keyOfStationDestination) &&
                Objects.equals(nameOfStationDestination, wagon.nameOfStationDestination) &&
                Objects.equals(cargo, wagon.cargo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, wagonType, keyOfStationDestination, nameOfStationDestination, volume, cargo);
    }

    @Override
    public String toString() {
        return "Wagon{" +
                "numberOfWagon='" + numberOfWagon + '\'' +
                ", wagonType=" + wagonType +
                ", keyOfStationDestination='" + keyOfStationDestination + '\'' +
                ", nameOfStationDestination='" + nameOfStationDestination + '\'' +
                ", volume=" + volume +
                ", cargo=" + cargo +
                '}';
    }
}
