package com.uraltranscom.dynamicdistributionpark.model;

import com.uraltranscom.dynamicdistributionpark.model.additional_model.CargoClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;

/**
 *
 * Класс Доходности
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class RateClass implements Comparable<RateClass> {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Route.class);

    private String nameOfStationDeparture; // Станция отправления
    private String nameOfStationDestination; // Станция назначения
    private String customer; // Заказчик
    private CargoClass cargo; // Груз
    private double rate; // Ставка
    private Date loadingDate; // Дата погрузки

    public RateClass(String nameOfStationDeparture, String nameOfStationDestination, String customer, String nameCargo, String keyCargo, double rate, Date loadingDate) {
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.nameOfStationDestination = nameOfStationDestination;
        this.customer = customer;
        this.cargo = new CargoClass(nameCargo, keyCargo);
        this.rate = rate;
        this.loadingDate = loadingDate;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public CargoClass getCargo() {
        return cargo;
    }

    public void setCargo(CargoClass cargo) {
        this.cargo = cargo;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(Date loadingDate) {
        this.loadingDate = loadingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateClass rateClass = (RateClass) o;
        return Double.compare(rateClass.rate, rate) == 0 &&
                Objects.equals(nameOfStationDeparture, rateClass.nameOfStationDeparture) &&
                Objects.equals(nameOfStationDestination, rateClass.nameOfStationDestination) &&
                Objects.equals(customer, rateClass.customer) &&
                Objects.equals(cargo, rateClass.cargo) &&
                Objects.equals(loadingDate, rateClass.loadingDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nameOfStationDeparture, nameOfStationDestination, customer, cargo, rate, loadingDate);
    }

    @Override
    public String toString() {
        return "RateClass{" +
                "nameOfStationDeparture='" + nameOfStationDeparture + '\'' +
                ", nameOfStationDestination='" + nameOfStationDestination + '\'' +
                ", customer='" + customer + '\'' +
                ", cargo=" + cargo +
                ", rate=" + rate +
                ", loadingDate=" + loadingDate +
                '}';
    }

    @Override
    public int compareTo(RateClass o) {
        return this.loadingDate.compareTo(o.loadingDate);
    }
}
