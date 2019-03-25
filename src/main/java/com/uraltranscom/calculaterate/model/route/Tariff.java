package com.uraltranscom.calculaterate.model.route;

import lombok.*;


/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 2018-12-27
 */

@Data
@AllArgsConstructor
public class Tariff {
    private float tariff;
    private float flagDownloadFromDB;
}
