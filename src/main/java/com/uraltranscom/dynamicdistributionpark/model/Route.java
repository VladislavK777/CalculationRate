package com.uraltranscom.dynamicdistributionpark.model;

import com.uraltranscom.dynamicdistributionpark.model.additional_model.CargoClass;
import com.uraltranscom.dynamicdistributionpark.model.additional_model.VolumePeriod;
import com.uraltranscom.dynamicdistributionpark.model.additional_model.WagonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс Маршрута
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class Route {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Route.class);

    private String keyOfStationDeparture; // Код станции отправления
    private String nameOfStationDeparture; // Станция отправления
    private String roadOfStationDeparture; // Дорога отправления
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Станция назначения
    private String roadOfStationDestination; // Дорога назначения
    private String distanceOfWay; // Расстояние маршрута
    private String customer; // Заказчик
    private int countOrders; // Количество заявок на маршрут
    private VolumePeriod volumePeriod; // Возможны объем вагона в заявке
    private String numberOrder; // Номер заявки
    private CargoClass cargo; // Груз
    private WagonType wagonType; // Тип вагона

    public Route(String keyOfStationDeparture,
                 String nameOfStationDeparture,
                 String roadOfStationDeparture,
                 String keyOfStationDestination,
                 String nameOfStationDestination,
                 String roadOfStationDestination,
                 String distanceOfWay,
                 String customer,
                 int countOrders,
                 int volumeFrom, int volumeTo,
                 String numberOrder,
                 String nameCargo, String keyCargo,
                 String wagonType) {
        this.keyOfStationDeparture = keyOfStationDeparture;
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.roadOfStationDeparture = roadOfStationDeparture;
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.roadOfStationDestination = roadOfStationDestination;
        this.distanceOfWay = distanceOfWay;
        this.customer = customer;
        this.countOrders = countOrders;
        this.volumePeriod = new VolumePeriod(volumeFrom, volumeTo);
        this.numberOrder = numberOrder;
        this.cargo = new CargoClass(nameCargo, keyCargo);
        this.wagonType = new WagonType(wagonType);
    }

    public Route(String keyOfStationDeparture,
                 String nameOfStationDeparture,
                 String roadOfStationDeparture,
                 String keyOfStationDestination,
                 String nameOfStationDestination,
                 String roadOfStationDestination,
                 String distanceOfWay,
                 String customer,
                 int volumeFrom, int volumeTo,
                 String nameCargo, String keyCargo) {
        this.keyOfStationDeparture = keyOfStationDeparture;
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.roadOfStationDeparture = roadOfStationDeparture;
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.roadOfStationDestination = roadOfStationDestination;
        this.distanceOfWay = distanceOfWay;
        this.customer = customer;
        this.volumePeriod = new VolumePeriod(volumeFrom, volumeTo);
        this.cargo = new CargoClass(nameCargo, keyCargo);
    }

    public String getKeyOfStationDeparture() {
        return keyOfStationDeparture;
    }

    public void setKeyOfStationDeparture(String keyOfStationDeparture) {
        this.keyOfStationDeparture = keyOfStationDeparture;
    }

    public String getNameOfStationDeparture() {
        return nameOfStationDeparture;
    }

    public void setNameOfStationDeparture(String nameOfStationDeparture) {
        this.nameOfStationDeparture = nameOfStationDeparture;
    }

    public String getRoadOfStationDeparture() {
        return roadOfStationDeparture;
    }

    public void setRoadOfStationDeparture(String roadOfStationDeparture) {
        this.roadOfStationDeparture = roadOfStationDeparture;
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

    public String getRoadOfStationDestination() {
        return roadOfStationDestination;
    }

    public void setRoadOfStationDestination(String roadOfStationDestination) {
        this.roadOfStationDestination = roadOfStationDestination;
    }

    public String getDistanceOfWay() {
        return distanceOfWay;
    }

    public void setDistanceOfWay(String distanceOfWay) {
        this.distanceOfWay = distanceOfWay;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public VolumePeriod getVolumePeriod() {
        return volumePeriod;
    }

    public void setVolumePeriod(VolumePeriod volumePeriod) {
        this.volumePeriod = volumePeriod;
    }

    public String getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(String numberOrder) {
        this.numberOrder = numberOrder;
    }

    public CargoClass getCargo() {
        return cargo;
    }

    public void setCargo(CargoClass cargo) {
        this.cargo = cargo;
    }

    public WagonType getWagonType() {
        return wagonType;
    }

    public void setWagonType(WagonType wagonType) {
        this.wagonType = wagonType;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(int countOrders) {
        this.countOrders = countOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return countOrders == route.countOrders &&
                Objects.equals(keyOfStationDeparture, route.keyOfStationDeparture) &&
                Objects.equals(nameOfStationDeparture, route.nameOfStationDeparture) &&
                Objects.equals(roadOfStationDeparture, route.roadOfStationDeparture) &&
                Objects.equals(keyOfStationDestination, route.keyOfStationDestination) &&
                Objects.equals(nameOfStationDestination, route.nameOfStationDestination) &&
                Objects.equals(roadOfStationDestination, route.roadOfStationDestination) &&
                Objects.equals(distanceOfWay, route.distanceOfWay) &&
                Objects.equals(customer, route.customer) &&
                Objects.equals(volumePeriod, route.volumePeriod) &&
                Objects.equals(numberOrder, route.numberOrder) &&
                Objects.equals(cargo, route.cargo) &&
                Objects.equals(wagonType, route.wagonType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyOfStationDeparture, nameOfStationDeparture, roadOfStationDeparture, keyOfStationDestination, nameOfStationDestination, roadOfStationDestination, distanceOfWay, customer, countOrders, volumePeriod, numberOrder, cargo, wagonType);
    }

    @Override
    public String toString() {
        return "Route{" +
                "keyOfStationDeparture='" + keyOfStationDeparture + '\'' +
                ", nameOfStationDeparture='" + nameOfStationDeparture + '\'' +
                ", roadOfStationDeparture='" + roadOfStationDeparture + '\'' +
                ", keyOfStationDestination='" + keyOfStationDestination + '\'' +
                ", nameOfStationDestination='" + nameOfStationDestination + '\'' +
                ", roadOfStationDestination='" + roadOfStationDestination + '\'' +
                ", distanceOfWay='" + distanceOfWay + '\'' +
                ", customer='" + customer + '\'' +
                ", countOrders=" + countOrders +
                ", volumePeriod=" + volumePeriod +
                ", numberOrder='" + numberOrder + '\'' +
                ", cargo=" + cargo +
                ", wagonType=" + wagonType +
                '}';
    }
}
