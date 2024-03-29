package com.uraltranscom.calculaterate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 2019-03-20
 */

public class CheckEmptyParamConvertNull {
    private static Logger logger = LoggerFactory.getLogger(CheckEmptyParamConvertNull.class);

    public static Object checkEmptyParamConvert(Object o) {
        return o.equals("") ? null : o;
    }
}
