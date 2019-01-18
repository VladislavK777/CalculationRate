package com.uraltranscom.calculaterate.model.settings;

import com.uraltranscom.calculaterate.model.Road;
import lombok.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SettingReturnStations {
    private int id;
    private int num;
    private Road road;
    private String idStationString;
    private String volumeGroupsString;
    private String idStationReturn;
    private String nameStationReturn;
}
