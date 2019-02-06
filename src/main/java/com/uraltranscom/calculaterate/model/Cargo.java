package com.uraltranscom.calculaterate.model;

import lombok.*;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {
    private String idCargo; //Код груза
    private String nameCargo; //Груз
}
