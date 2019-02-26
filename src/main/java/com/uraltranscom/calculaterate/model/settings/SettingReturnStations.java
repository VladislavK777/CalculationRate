package com.uraltranscom.calculaterate.model.settings;

import com.uraltranscom.calculaterate.util.WrapperArrayToString;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Data
@NoArgsConstructor
public class SettingReturnStations {
    private int id;
    private String namesRoad;
    private String idsStationString;
    private Integer[] idsDepartment;
    private String[] namesDepartment;
    private String namesDepartmentString;
    private String volumeGroupsString;
    private String idStationReturn;
    private String nameStationReturn;

    public SettingReturnStations(int id, String namesRoad, String idsStationString, Integer[] idsDepartment, String[] namesDepartment, String volumeGroupsString, String idStationReturn, String nameStationReturn) {
        this.id = id;
        this.namesRoad = namesRoad;
        this.idsStationString = idsStationString;
        this.idsDepartment = idsDepartment;
        this.namesDepartment = namesDepartment;
        this.namesDepartmentString = WrapperArrayToString.wrapperArray(namesDepartment);
        this.volumeGroupsString = volumeGroupsString;
        this.idStationReturn = idStationReturn;
        this.nameStationReturn = nameStationReturn;
    }
}
