package com.uraltranscom.calculaterate.model.route;

import lombok.*;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 24.12.2018
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {
    private String idCargo; //Код груза
    private String nameCargo; //Груз
}
