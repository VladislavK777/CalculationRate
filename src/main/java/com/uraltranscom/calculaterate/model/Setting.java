package com.uraltranscom.calculaterate.model;

import lombok.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Setting {
    private int num;
    private String idRoad;
    private String shortNameRoad;
    private String idStationString;
    private String volumeGroupsString;
    private String idStationReturn;
    private String nameStationReturn;
}
