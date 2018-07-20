package com.uraltranscom.dynamicdistributionpark.model.additional_model;

import com.uraltranscom.dynamicdistributionpark.service.impl.GetTypeOfCargoImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 *
 * Класс Груза
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class CargoClass {
    @Autowired
    private GetTypeOfCargoImpl getTypeOfCargo;

    private String nameCargo; // Груз
    private String keyCargo; // Код груза
    private int cargoType; // Класс груза

    public CargoClass(String nameCargo, String keyCargo) {
        this.nameCargo = nameCargo;
        this.keyCargo = keyCargo;
        this.cargoType = getTypeOfCargo.getTypeOfCargo(nameCargo);
    }

    public String getNameCargo() {
        return nameCargo;
    }

    public void setNameCargo(String nameCargo) {
        this.nameCargo = nameCargo;
    }

    public String getKeyCargo() {
        return keyCargo;
    }

    public void setKeyCargo(String keyCargo) {
        this.keyCargo = keyCargo;
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
        CargoClass that = (CargoClass) o;
        return cargoType == that.cargoType &&
                Objects.equals(getTypeOfCargo, that.getTypeOfCargo) &&
                Objects.equals(nameCargo, that.nameCargo) &&
                Objects.equals(keyCargo, that.keyCargo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTypeOfCargo, nameCargo, keyCargo, cargoType);
    }
}
