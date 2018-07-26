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

    public WagonRateAndTariff(String numberOfWagon, double rate, double tariff) {
        this.numberOfWagon = numberOfWagon;
        this.rate = rate;
        this.tariff = tariff;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WagonRateAndTariff that = (WagonRateAndTariff) o;
        return Double.compare(that.rate, rate) == 0 &&
                Double.compare(that.tariff, tariff) == 0 &&
                Objects.equals(numberOfWagon, that.numberOfWagon);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, rate, tariff);
    }

    @Override
    public String toString() {
        return "WagonRateAndTariff{" +
                "numberOfWagon='" + numberOfWagon + '\'' +
                ", rate=" + rate +
                ", tariff=" + tariff +
                '}';
    }
}
