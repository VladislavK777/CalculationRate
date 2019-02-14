package com.uraltranscom.calculaterate.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 13.02.2019
 */

public class GetVolumeGroupTest {

    @Test
    public void testGetVolumeGroup() {
        int group = GetVolumeGroup.getVolumeGroup(122);
        Assert.assertEquals(120, group);
    }
}
