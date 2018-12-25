package com.uraltranscom.calculaterate.service.additional;

import com.uraltranscom.calculaterate.util.GetPathSaveFile;
import com.uraltranscom.calculaterate.util.PropertyUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    // Список дорог, с которых расчет идет от станции Отправления
    public static final List<String> LIST_ROADS_WITHOUT_CHECK_DIST = Arrays.stream(new String[]{"МОСК", "ОКТ", "ПРИВ", "БЧ", "Ю-УР", "З-СИБ", "В-СИБ", "ГОРЬК", "Ю-ВОСТ"}).collect(Collectors.toList());

    // Список дорог, с которых расчет идет проверка на мин расстояние от станции Отправления
    public static final List<String> LIST_ROADS_WITH_CHECK_DIST = Arrays.stream(new String[]{"СЕВ", "КБШ"}).collect(Collectors.toList());

    // Список опорных станций, с которых идет расчет
    public static final List<String> LIST_STATIONS_WITH_CHECK_DIST = Arrays.stream(new String[]{"193805", "612003", "806708"}).collect(Collectors.toList());

}
