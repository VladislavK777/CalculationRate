package com.uraltranscom.calculaterate.model;

import lombok.*;


/**
 * @author Vladislav Klochkov
 * @create 2018-12-27
 */

@Data
@AllArgsConstructor
public class Tariff {
    private float tariff;
    private float flagDownloadFromDB;
}
