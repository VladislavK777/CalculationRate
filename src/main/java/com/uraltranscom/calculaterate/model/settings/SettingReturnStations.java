package com.uraltranscom.calculaterate.model.settings;

import lombok.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingReturnStations {
    private int id;
    private String namesRoad;
    private String idsStationString;
    private String volumeGroupsString;
    private String idStationReturn;
    private String nameStationReturn;
}
