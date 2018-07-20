package com.uraltranscom.dynamicdistributionpark.model;

import com.uraltranscom.dynamicdistributionpark.service.impl.GetTypeOfCargoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 *
 * Класс Порожнего рейса
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class EmptyRoute {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Route.class);
    @Autowired
    private GetTypeOfCargoImpl getTypeOfCargo;

    private String nameOfStationDeparture; // Станция отправления
    private String nameOfStationDestination; // Станция назначения
    private String cargo; // Груз
    private double tariff; // Тариф
    private int cargoType; // Класс груза

    public EmptyRoute(String nameOfStationDeparture, String nameOfStationDestination, String cargo, double tariff) {
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.nameOfStationDestination = nameOfStationDestination;
        this.cargo = cargo;
        this.tariff = tariff;
        this.cargoType = getTypeOfCargo.getTypeOfCargo(this.cargo);
    }

    public String getNameOfStationDeparture() {
        return nameOfStationDeparture;
    }

    public void setNameOfStationDeparture(String nameOfStationDeparture) {
        this.nameOfStationDeparture = nameOfStationDeparture;
    }

    public String getNameOfStationDestination() {
        return nameOfStationDestination;
    }

    public void setNameOfStationDestination(String nameOfStationDestination) {
        this.nameOfStationDestination = nameOfStationDestination;
    }

    public GetTypeOfCargoImpl getGetTypeOfCargo() {
        return getTypeOfCargo;
    }

    public void setGetTypeOfCargo(GetTypeOfCargoImpl getTypeOfCargo) {
        this.getTypeOfCargo = getTypeOfCargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getTariff() {
        return tariff;
    }

    public void setTariff(double tariff) {
        this.tariff = tariff;
    }

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmptyRoute that = (EmptyRoute) o;
        return Double.compare(that.tariff, tariff) == 0 &&
                cargoType == that.cargoType &&
                Objects.equals(nameOfStationDeparture, that.nameOfStationDeparture) &&
                Objects.equals(nameOfStationDestination, that.nameOfStationDestination) &&
                Objects.equals(cargo, that.cargo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nameOfStationDeparture, nameOfStationDestination, cargo, tariff, cargoType);
    }

    @Override
    public String toString() {
        return "EmptyRoute{" +
                ", nameOfStationDeparture='" + nameOfStationDeparture + '\'' +
                ", nameOfStationDestination='" + nameOfStationDestination + '\'' +
                ", cargo='" + cargo + '\'' +
                ", tariff=" + tariff +
                ", cargoType=" + cargoType +
                '}';
    }
}
