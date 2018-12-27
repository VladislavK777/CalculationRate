package com.uraltranscom.calculaterate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Vladislav Klochkov
 * @create 2018-12-27
 */

@Getter
@Setter
@AllArgsConstructor
public class Tariff {
    private double tariff;
    private int flagDownloadFromDB;
}
