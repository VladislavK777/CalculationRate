package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author Vladislav Klochkov
 * @create 2018-12-27
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Tariff {
    private float tariff;
    private float flagDownloadFromDB;
}
