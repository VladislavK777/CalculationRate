package com.uraltranscom.dynamicdistributionpark.service.additional;

import com.uraltranscom.dynamicdistributionpark.util.PropertyUtil;

/**
 *
 * Класс-помощник содержит основные константы
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class JavaHelperBase {
    private static PropertyUtil propertyUtil = new PropertyUtil();

    public static final int LOADING_WAGON = Integer.parseInt(propertyUtil.getProperty("loadingwagon"));
    public static final int UNLOADING_WAGON = Integer.parseInt(propertyUtil.getProperty("unloadingwagon"));

    // Максимальное количетво дней в обороте вагона
    public static final int MAX_COUNT_DAYS = 31;

    // Максимальное количество дней в декаде
    public static final int MAX_COUNT_DAYS_DECADE = 40;

    // Типы вагонов
    public static final String TYPE_OF_WAGON_KR = "КР";
    public static final String TYPE_OF_WAGON_PV = "ПВ";

    // Константы для класса преобразования префикла "дней"
    public static final String PREFIX_ONE_DAY = "день";
    public static final String PREFIX_2_4_DAYS = "дня";
    public static final String PREFIX_5_10_DAYS = "дней";

    // Максимальное расстояние для пустого вагона

    public static final int MAX_DISTANCE_RUS_TO_RUS_CLASS3 = Integer.parseInt(propertyUtil.getProperty("distance.rustorus.cargoclass3")); // Внутри России груз класса 3
    public static final int MAX_DISTANCE_RUS_TO_RUS = Integer.parseInt(propertyUtil.getProperty("distance.rustorus.cargoclassall")); // Внутри России
    public static final int MAX_DISTANCE_CIS_TO_CIS = Integer.parseInt(propertyUtil.getProperty("distance.cistocis.cargoclassall")); // Внутри СНГ
    public static final int MAX_DISTANCE_CIS_TO_RUS = Integer.parseInt(propertyUtil.getProperty("distance.cistorus.cargoclassall")); // Между Россией_СНГ_Россией

    // Код страны
    public static final int CODE_IS_RUSSIA = 11;

    // Коды проверок принадлежности стран
    public static final int RUS_RUS = 0; // Внутри России
    public static final int CIS_CIS = 1; // Внутри СНГ
    public static final int CIS_RUS = 2; // СНГ_Россией
}
