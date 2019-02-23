package com.uraltranscom.calculaterate.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate
 * @date 21.02.2019
 */
public class WrapperArrayToStringTest {
    String[] testStr = new String[]{"1", "2"};
    @Test
    public void test() {
        Assert.assertEquals("1,2", WrapperArrayToString.wrapperArray(testStr));
    }
}
