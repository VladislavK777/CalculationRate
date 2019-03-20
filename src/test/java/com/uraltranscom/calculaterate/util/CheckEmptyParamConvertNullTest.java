package com.uraltranscom.calculaterate.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladislav Klochkov
 * @create 2019-03-20
 */

public class CheckEmptyParamConvertNullTest {
    @Test
    public void test() {
        Assert.assertNotNull("It's not ok", CheckEmptyParamConvertNull.checkEmptyParamConvert("test"));
        Assert.assertNull("It's ok", CheckEmptyParamConvertNull.checkEmptyParamConvert(""));
    }
}
