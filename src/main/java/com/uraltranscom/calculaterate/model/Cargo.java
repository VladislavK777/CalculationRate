package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * Класс Груза
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 24.12.2018
 *
 * 24.12.2018
 *   1. Версия 1.0
 *
 */

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Cargo {
    private String idCargo; //Код груза
    private String cargoType;  //Класс груза
    private String nameCargo; //Груз

    @Override
    public String toString() {
        return "Cargo{" +
                "idCargo='" + idCargo + '\'' +
                ", cargoType='" + cargoType + '\'' +
                ", nameCargo='" + nameCargo + '\'' +
                '}';
    }
}
