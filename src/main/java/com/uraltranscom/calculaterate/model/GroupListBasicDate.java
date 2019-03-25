package com.uraltranscom.calculaterate.model;

import com.uraltranscom.calculaterate.model.route.Cargo;
import com.uraltranscom.calculaterate.model.station.Station;
import com.uraltranscom.calculaterate.util.GetVolumeGroup;
import lombok.Data;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 05.03.2019
 */

@Data
public class GroupListBasicDate {
    private int number;
    private Station stationFrom;
    private Station stationTo;
    private Cargo cargo;
    private int volumeGroup;

    public GroupListBasicDate(int number, Station stationFrom, Station stationTo, Cargo cargo, int volume) {
        this.number = number;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.cargo = cargo;
        this.volumeGroup = GetVolumeGroup.getVolumeGroup(volume);
    }
}
