package com.uraltranscom.dynamicdistributionpark.model_ext;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс для формирвоания итоговой информации для вагона
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class WagonFinalInfo {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WagonFinalInfo.class);

    private String numberOfWagon; // Номер вагона
    private int countCircleDays; // Количество дней в пути
    private int distanceEmpty; // Расстояние порожнее
    private String nameOfStationDepartureOfWagon; // Станция, куда едет вагон порожний
    private Route route; // Маршрут
    private int cargoType; // Класс груза
    private double rate; // Ставка
    private double tariff; // Тариф


    // Первичный конструктор, без ставки и тарифа
    public WagonFinalInfo(String numberOfWagon, int countCircleDays, int distanceEmpty, String nameOfStationDepartureOfWagon, Route route, int cargoType) {
        this.numberOfWagon = numberOfWagon;
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
        this.route = route;
        this.cargoType = cargoType;
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
    }

    public int getCountCircleDays() {
        return countCircleDays;
    }

    public void setCountCircleDays(int countCircleDays) {
        this.countCircleDays = countCircleDays;
    }

    public int getDistanceEmpty() {
        return distanceEmpty;
    }

    public void setDistanceEmpty(int distanceEmpty) {
        this.distanceEmpty = distanceEmpty;
    }

    public String getNameOfStationDepartureOfWagon() {
        return nameOfStationDepartureOfWagon;
    }

    public void setNameOfStationDepartureOfWagon(String nameOfStationDepartureOfWagon) {
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getTariff() {
        return tariff;
    }

    public void setTariff(double tariff) {
        this.tariff = tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WagonFinalInfo that = (WagonFinalInfo) o;
        return countCircleDays == that.countCircleDays &&
                distanceEmpty == that.distanceEmpty &&
                cargoType == that.cargoType &&
                Double.compare(that.rate, rate) == 0 &&
                Double.compare(that.tariff, tariff) == 0 &&
                Objects.equals(numberOfWagon, that.numberOfWagon) &&
                Objects.equals(nameOfStationDepartureOfWagon, that.nameOfStationDepartureOfWagon) &&
                Objects.equals(route, that.route);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, countCircleDays, distanceEmpty, nameOfStationDepartureOfWagon, route, cargoType, rate, tariff);
    }

    @Override
    public String toString() {
        return "WagonFinalInfo{" +
                "numberOfWagon='" + numberOfWagon + '\'' +
                ", countCircleDays=" + countCircleDays +
                ", distanceEmpty=" + distanceEmpty +
                ", nameOfStationDepartureOfWagon='" + nameOfStationDepartureOfWagon + '\'' +
                ", route=" + route +
                ", cargoType=" + cargoType +
                ", rate=" + rate +
                ", tariff=" + tariff +
                '}';
    }
}
