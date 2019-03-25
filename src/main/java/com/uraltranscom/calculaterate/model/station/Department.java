package com.uraltranscom.calculaterate.model.station;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 21.02.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int idDepartment;
    private String nameDepartment;
}
