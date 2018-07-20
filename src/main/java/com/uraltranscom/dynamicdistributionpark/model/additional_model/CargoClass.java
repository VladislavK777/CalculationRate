package com.uraltranscom.dynamicdistributionpark.model.additional_model;

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
    private String nameCargo; // Груз
    private String keyCargo; // Код груза

    public CargoClass(String nameCargo, String keyCargo) {
        this.nameCargo = nameCargo;
        this.keyCargo = keyCargo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoClass that = (CargoClass) o;
        return Objects.equals(nameCargo, that.nameCargo) &&
                Objects.equals(keyCargo, that.keyCargo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nameCargo, keyCargo);
    }
}
