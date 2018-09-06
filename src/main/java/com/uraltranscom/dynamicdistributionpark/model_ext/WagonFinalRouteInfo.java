package com.uraltranscom.dynamicdistributionpark.model_ext;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.additional_model.CargoClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс для формирвоания итоговой информации маршрутов для вагона
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 27.07.2018
 *
 * 27.07.2018
 *   1. Версия 1.0
 *
 */

public class WagonFinalRouteInfo {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WagonFinalRouteInfo.class);

    private int countCircleDays; // Количество дней в пути
    private int distanceEmpty; // Расстояние порожнее
    private String currentNameOfStationOfWagon; // Текущая станция вагона
    private String currentKeyOfStationOfWagon; // Код текущей станции
    private String nameOfStationDepartureOfWagon; // Станция, куда едет вагон порожний
    private String keyOfStationDepartureOfWagon; // Код станции распределения
    private Route route; // Маршрут
    private CargoClass cargo; // Предыдущий груз
    private int cargoType; // Класс предыдущего груза
    private Object rate; // Ставка
    private Object tariff; // Тариф
    private boolean isEmpty; // Флаг пустоты значения
    private boolean isLoadingRateFromDB; // Признак загрузки ставки из БД
    private boolean isLoadingTariffFromDB; // Признак загрузки тарифа из БД

    // Первичный конструктор, без ставки и тарифа
    public WagonFinalRouteInfo(int countCircleDays,
                              int distanceEmpty,
                              String currentNameOfStationOfWagon,
                              String currentKeyOfStationOfWagon,
                              String nameOfStationDepartureOfWagon,
                              String keyOfStationDepartureOfWagon,
                              Route route,
                              CargoClass cargo,
                              int cargoType) {
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
        this.currentNameOfStationOfWagon = currentNameOfStationOfWagon;
        this.currentKeyOfStationOfWagon = currentKeyOfStationOfWagon;
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
        this.keyOfStationDepartureOfWagon = keyOfStationDepartureOfWagon;
        this.route = route;
        this.cargo = cargo;
        this.cargoType = cargoType;
        this.isEmpty = false;
        this.isLoadingRateFromDB = false;
        this.isLoadingTariffFromDB = false;
    }

    public WagonFinalRouteInfo(int countCircleDays,
                               int distanceEmpty,
                               String currentNameOfStationOfWagon,
                               String currentKeyOfStationOfWagon,
                               String nameOfStationDepartureOfWagon,
                               String keyOfStationDepartureOfWagon,
                               Route route, CargoClass cargo,
                               int cargoType,
                               boolean isEmpty,
                               boolean isLoadingRateFromDB,
                               boolean isLoadingTariffFromDB) {
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
        this.currentNameOfStationOfWagon = currentNameOfStationOfWagon;
        this.currentKeyOfStationOfWagon = currentKeyOfStationOfWagon;
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
        this.keyOfStationDepartureOfWagon = keyOfStationDepartureOfWagon;
        this.route = route;
        this.cargo = cargo;
        this.cargoType = cargoType;
        this.isEmpty = isEmpty;
        this.isLoadingRateFromDB = isLoadingRateFromDB;
        this.isLoadingTariffFromDB = isLoadingTariffFromDB;
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

    public String getCurrentNameOfStationOfWagon() {
        return currentNameOfStationOfWagon;
    }

    public void setCurrentNameOfStationOfWagon(String currentNameOfStationOfWagon) {
        this.currentNameOfStationOfWagon = currentNameOfStationOfWagon;
    }

    public String getCurrentKeyOfStationOfWagon() {
        return currentKeyOfStationOfWagon;
    }

    public void setCurrentKeyOfStationOfWagon(String currentKeyOfStationOfWagon) {
        this.currentKeyOfStationOfWagon = currentKeyOfStationOfWagon;
    }

    public String getNameOfStationDepartureOfWagon() {
        return nameOfStationDepartureOfWagon;
    }

    public void setNameOfStationDepartureOfWagon(String nameOfStationDepartureOfWagon) {
        this.nameOfStationDepartureOfWagon = nameOfStationDepartureOfWagon;
    }

    public String getKeyOfStationDepartureOfWagon() {
        return keyOfStationDepartureOfWagon;
    }

    public void setKeyOfStationDepartureOfWagon(String keyOfStationDepartureOfWagon) {
        this.keyOfStationDepartureOfWagon = keyOfStationDepartureOfWagon;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public CargoClass getCargo() {
        return cargo;
    }

    public void setCargo(CargoClass cargo) {
        this.cargo = cargo;
    }

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }

    public Object getRate() {
        return rate;
    }

    public void setRate(Object rate) {
        this.rate = rate;
    }

    public Object getTariff() {
        return tariff;
    }

    public void setTariff(Object tariff) {
        this.tariff = tariff;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isLoadingRateFromDB() {
        return isLoadingRateFromDB;
    }

    public void setLoadingRateFromDB(boolean loadingRateFromDB) {
        isLoadingRateFromDB = loadingRateFromDB;
    }

    public boolean isLoadingTariffFromDB() {
        return isLoadingTariffFromDB;
    }

    public void setLoadingTariffFromDB(boolean loadingTariffFromDB) {
        isLoadingTariffFromDB = loadingTariffFromDB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WagonFinalRouteInfo that = (WagonFinalRouteInfo) o;
        return countCircleDays == that.countCircleDays &&
                distanceEmpty == that.distanceEmpty &&
                cargoType == that.cargoType &&
                isEmpty == that.isEmpty &&
                isLoadingRateFromDB == that.isLoadingRateFromDB &&
                isLoadingTariffFromDB == that.isLoadingTariffFromDB &&
                Objects.equals(currentNameOfStationOfWagon, that.currentNameOfStationOfWagon) &&
                Objects.equals(currentKeyOfStationOfWagon, that.currentKeyOfStationOfWagon) &&
                Objects.equals(nameOfStationDepartureOfWagon, that.nameOfStationDepartureOfWagon) &&
                Objects.equals(keyOfStationDepartureOfWagon, that.keyOfStationDepartureOfWagon) &&
                Objects.equals(route, that.route) &&
                Objects.equals(cargo, that.cargo) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(tariff, that.tariff);
    }

    @Override
    public int hashCode() {

        return Objects.hash(countCircleDays, distanceEmpty, currentNameOfStationOfWagon, currentKeyOfStationOfWagon, nameOfStationDepartureOfWagon, keyOfStationDepartureOfWagon, route, cargo, cargoType, rate, tariff, isEmpty, isLoadingRateFromDB, isLoadingTariffFromDB);
    }

    @Override
    public String toString() {
        return "WagonFinalRouteInfo{" +
                "countCircleDays=" + countCircleDays +
                ", distanceEmpty=" + distanceEmpty +
                ", currentNameOfStationOfWagon='" + currentNameOfStationOfWagon + '\'' +
                ", currentKeyOfStationOfWagon='" + currentKeyOfStationOfWagon + '\'' +
                ", nameOfStationDepartureOfWagon='" + nameOfStationDepartureOfWagon + '\'' +
                ", keyOfStationDepartureOfWagon='" + keyOfStationDepartureOfWagon + '\'' +
                ", route=" + route +
                ", cargo=" + cargo +
                ", cargoType=" + cargoType +
                ", rate=" + rate +
                ", tariff=" + tariff +
                ", isEmpty=" + isEmpty +
                ", isLoadingRateFromDB=" + isLoadingRateFromDB +
                ", isLoadingTariffFromDB=" + isLoadingTariffFromDB +
                '}';
    }
}
