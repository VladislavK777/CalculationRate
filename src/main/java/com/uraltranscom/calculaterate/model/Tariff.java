package com.uraltranscom.calculaterate.model;

import lombok.*;


/**
 * @author Vladislav Klochkov
 * @create 2018-12-27
 */

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tariff {
    private float tariff;
    private float flagDownloadFromDB;
}
