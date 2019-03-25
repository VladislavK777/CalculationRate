package com.uraltranscom.calculaterate.service.additional;

import com.uraltranscom.calculaterate.util.PropertyUtil;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 24.12.2018
 */

public class JavaHelperBase {
    private static PropertyUtil propertyUtil = new PropertyUtil();

    // Константы для класса преобразования префикла "дней"
    public static final String PREFIX_ONE_DAY = "день";
    public static final String PREFIX_2_4_DAYS = "дня";
    public static final String PREFIX_5_10_DAYS = "дней";

    public static final String ZOOKEEPER_HOST = propertyUtil.getProperty("common.zookeeperhost");
    public static final String ZOOKEEPER_SECRET_KEY = propertyUtil.getProperty("common.secretkey");
}
