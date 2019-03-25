package com.uraltranscom.calculaterate.model.station;

import lombok.*;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 24.12.2018
 */

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Road {
    private int idRoad; //Идентификатор дороги
    private String nameRoad; //Название дороги
}
