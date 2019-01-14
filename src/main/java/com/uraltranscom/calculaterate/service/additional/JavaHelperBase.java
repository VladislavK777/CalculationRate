package com.uraltranscom.calculaterate.service.additional;

import com.uraltranscom.calculaterate.util.PropertyUtil;

/**
 *
 * Класс-помощник содержит основные константы
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 24.12.2018
 *
 * 24.12.2018
 *   1. Версия 1.0
 *
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
