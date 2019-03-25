package com.uraltranscom.calculaterate.model.route;

import lombok.*;

import static com.uraltranscom.calculaterate.util.GetVolumeGroup.getVolumeGroup;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 2019-01-07
 */

@Data
public class Volume {
    private int value;
    private int group;

    public Volume(int value) {
        this.value = value;
        this.group = getVolumeGroup(value);
    }
}
