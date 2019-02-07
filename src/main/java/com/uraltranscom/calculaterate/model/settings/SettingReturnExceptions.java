package com.uraltranscom.calculaterate.model.settings;

import com.uraltranscom.calculaterate.model.Cargo;
import com.uraltranscom.calculaterate.model.Station;
import lombok.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingReturnExceptions {
    private int id;
    private int num;
    private String idsRoad;
    private String namesRoad;
    private String idsStationString;
    private String volumeGroupsString;
    private Station stationFrom;
    private Station stationTo;
    private Cargo cargo;
    private String cargoTypeString;
    private String routeType;
    private int distance;
    private int countDays;
    private double rate;
    private double tariff;
}
