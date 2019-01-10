package com.uraltranscom.calculaterate.util;

import org.junit.Assert;
import org.junit.Test;

import static com.uraltranscom.calculaterate.util.GetVolumeGroup.getVolumeGroup;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-07
 */

public class GetVolumeGroupTest {
    @Test
    public void getGroup() {
        Assert.assertEquals(120, getVolumeGroup(114));
    }
}
