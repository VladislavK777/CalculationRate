package com.uraltranscom.calculaterate.model.settings;

import com.uraltranscom.calculaterate.model.route.Cargo;
import com.uraltranscom.calculaterate.model.station.Station;
import com.uraltranscom.calculaterate.util.WrapperArrayToString;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 16.01.2019
 */

@Data
@NoArgsConstructor
public class SettingReturnExceptions {
    private int id;
    private int num;
    private String namesRoad;
    private String idsStationString;
    private Integer[] idsDepartment;
    private String[] namesDepartment;
    private String namesDepartmentString;
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

    public SettingReturnExceptions(int id, int num, String namesRoad, String idsStationString, Integer[] idsDepartment, String[] namesDepartment, String volumeGroupsString, Station stationFrom, Station stationTo, Cargo cargo, String cargoTypeString, String routeType, int distance, int countDays, double rate, double tariff) {
        this.id = id;
        this.num = num;
        this.namesRoad = namesRoad;
        this.idsStationString = idsStationString;
        this.idsDepartment = idsDepartment;
        this.namesDepartment = namesDepartment;
        this.namesDepartmentString = WrapperArrayToString.wrapperArray(namesDepartment);
        this.volumeGroupsString = volumeGroupsString;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.cargo = cargo;
        this.cargoTypeString = cargoTypeString;
        this.routeType = routeType;
        this.distance = distance;
        this.countDays = countDays;
        this.rate = rate;
        this.tariff = tariff;
    }
}
