package com.uraltranscom.dynamicdistributionpark.model.additional_model;

import java.util.Objects;

/**
 *
 * Дополнительный класс для добавления ставки и тарифа
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 *
 */

public class WagonRateAndTariff {
    String numberOfWagon;
    double rate;
    double tariff;
    String routeId;

    public WagonRateAndTariff(String numberOfWagon, double rate, double tariff, String routeId) {
        this.numberOfWagon = numberOfWagon;
        this.rate = rate;
        this.tariff = tariff;
        this.routeId = routeId;
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
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

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WagonRateAndTariff that = (WagonRateAndTariff) o;
        return Double.compare(that.rate, rate) == 0 &&
                Double.compare(that.tariff, tariff) == 0 &&
                Objects.equals(numberOfWagon, that.numberOfWagon) &&
                Objects.equals(routeId, that.routeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, rate, tariff, routeId);
    }

    @Override
    public String toString() {
        return "WagonRateAndTariff{" +
                "numberOfWagon='" + numberOfWagon + '\'' +
                ", rate=" + rate +
                ", tariff=" + tariff +
                ", routeId='" + routeId + '\'' +
                '}';
    }
}
