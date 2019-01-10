package com.uraltranscom.calculaterate.util;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-07
 */

public final class GetVolumeGroup {

    public static int getVolumeGroup(int value) {
        return (value == 114 || value == 120 || value == 122) ? 120 :
                (value == 138 || value == 140) ? 138 : 150;
    }
}
