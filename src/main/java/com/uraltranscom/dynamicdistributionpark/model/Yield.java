package com.uraltranscom.dynamicdistributionpark.model;

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

public class Yield {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Route.class);

    private String nameOfStationDeparture; // Станция отправления
    private String nameOfStationDestination; // Станция назначения
    private String customer; // Заказчик
    private String cargo; // Груз
    private double rate; // Ставка
    private Date loadingDate; // Дата погрузки

    public Yield(String nameOfStationDeparture, String nameOfStationDestination, String customer, String cargo, double rate, Date loadingDate) {
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.nameOfStationDestination = nameOfStationDestination;
        this.customer = customer;
        this.cargo = cargo;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
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
        Yield yield = (Yield) o;
        return Double.compare(yield.rate, rate) == 0 &&
                Objects.equals(nameOfStationDeparture, yield.nameOfStationDeparture) &&
                Objects.equals(nameOfStationDestination, yield.nameOfStationDestination) &&
                Objects.equals(customer, yield.customer) &&
                Objects.equals(cargo, yield.cargo) &&
                Objects.equals(loadingDate, yield.loadingDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nameOfStationDeparture, nameOfStationDestination, customer, cargo, rate, loadingDate);
    }

    @Override
    public String toString() {
        return "Yield{" +
                "nameOfStationDeparture='" + nameOfStationDeparture + '\'' +
                ", nameOfStationDestination='" + nameOfStationDestination + '\'' +
                ", customer='" + customer + '\'' +
                ", cargo='" + cargo + '\'' +
                ", rate=" + rate +
                ", loadingDate=" + loadingDate +
                '}';
    }
}
