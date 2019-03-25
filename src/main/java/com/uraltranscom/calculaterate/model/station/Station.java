package com.uraltranscom.calculaterate.model.station;

import lombok.*;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 24.12.2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    private String idStation; //Код станции
    private String nameStation; //Название станция
    private Road road; //Дорога станции
}
