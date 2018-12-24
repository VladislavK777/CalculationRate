package com.uraltranscom.calculaterate.service.additional;

import com.uraltranscom.calculaterate.util.GetPathSaveFile;
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

    // Количество дней для погрузки/выгрузки
    public static final int LOADING_WAGON = Integer.parseInt(propertyUtil.getProperty("loadingwagon"));
    public static final int LOADING_2_WAGON = Integer.parseInt(propertyUtil.getProperty("loading2wagon"));
    public static final int UNLOADING_WAGON = Integer.parseInt(propertyUtil.getProperty("unloadingwagon"));

    // Константы для класса преобразования префикла "дней"
    public static final String PREFIX_ONE_DAY = "день";
    public static final String PREFIX_2_4_DAYS = "дня";
    public static final String PREFIX_5_10_DAYS = "дней";

    // Путь к файлу серилизации сохраненных карт расстояний
    public static final String PATH_SAVE_FILE_MAP = GetPathSaveFile.getPathTomcat();

    // Параметры ZooKeeper
    public static final String ZOOKEEPER_HOST = propertyUtil.getProperty("common.zookeeperhost");
    public static final String ZOOKEEPER_SECRET_KEY = propertyUtil.getProperty("common.secretkey");

    // Строка соотношения расстояния ко дням
    public static final String DISTANCE_DAYS = propertyUtil.getProperty("distanceday");
}
