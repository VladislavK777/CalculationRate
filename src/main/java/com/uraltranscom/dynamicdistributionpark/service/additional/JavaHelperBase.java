package com.uraltranscom.dynamicdistributionpark.service.additional;

import com.uraltranscom.dynamicdistributionpark.util.GetPathSaveFile;
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

    // Количество дней для погрузки/выгрузки
    public static final int LOADING_WAGON = Integer.parseInt(propertyUtil.getProperty("loadingwagon"));
    public static final int UNLOADING_WAGON = Integer.parseInt(propertyUtil.getProperty("unloadingwagon"));

    // Статус вагонов
    public static final String STATUS_EMPTY = propertyUtil.getProperty("wagon.status.empty");
    public static final String STATUS_FULL = propertyUtil.getProperty("wagon.status.full");

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

    // Код страны Россия
    public static final int CODE_IS_RUSSIA = 11;

    // Коды проверок принадлежности стран
    public static final int RUS_RUS = 0; // Внутри России
    public static final int CIS_CIS = 1; // Внутри СНГ
    public static final int CIS_RUS = 2; // СНГ_Россией

    // Путь к файлу серилизации сохраненных карт расстояний
    public static final String PATH_SAVE_FILE_MAP = GetPathSaveFile.getPathTomcat();

    // Заголовки файла Тарифы
    public static final String TARIFF_NAME_STATION_DEPARTURE = propertyUtil.getProperty("tariffclass.namestationdeparture");
    public static final String TARIFF_NAME_STATION_DESTINATION = propertyUtil.getProperty("tariffclass.namestationdestination");
    public static final String TARIFF_LAST_CARGO = propertyUtil.getProperty("tariffclass.lastcargo");
    public static final String TARIFF_TARIFF = propertyUtil.getProperty("tariffclass.tariff");

    // Заголовки файла Ставки
    public static final String RATE_NAME_STATION_DEPARTURE = propertyUtil.getProperty("rateclass.namestationdeparture");
    public static final String RATE_NAME_STATION_DESTINATION = propertyUtil.getProperty("rateclass.namestationdestination");
    public static final String RATE_CUSTOMER = propertyUtil.getProperty("rateclass.customer");
    public static final String RATE_NAME_CARGO = propertyUtil.getProperty("rateclass.namecargo");
    public static final String RATE_KEY_CARGO = propertyUtil.getProperty("rateclass.keycargo");
    public static final String RATE_RATE = propertyUtil.getProperty("rateclass.rate");
    public static final String RATE_DATE_LOADING = propertyUtil.getProperty("rateclass.dateloading");

    // Заголовки файла Заявки
    public static final String ROUTE_KEY_STATION_DEPARTURE = propertyUtil.getProperty("route.keystationdeparture");
    public static final String ROUTE_NAME_STATION_DEPARTURE = propertyUtil.getProperty("route.namestationdeparture");
    public static final String ROUTE_ROAD_STATION_DEPARTURE = propertyUtil.getProperty("route.roadstationdeparture");
    public static final String ROUTE_KEY_STATION_DESTINATION = propertyUtil.getProperty("route.keystationdestination");
    public static final String ROUTE_NAME_STATION_DESTINATION = propertyUtil.getProperty("route.namestationdestination");
    public static final String ROUTE_ROAD_STATION_DESTINATION = propertyUtil.getProperty("route.roadstationdestination");
    public static final String ROUTE_DISTANCE = propertyUtil.getProperty("route.distanceway");
    public static final String ROUTE_CUSTOMER = propertyUtil.getProperty("route.customer");
    public static final String ROUTE_COUNT_ORDERS = propertyUtil.getProperty("route.countorders");
    public static final String ROUTE_VOLUME_FROM = propertyUtil.getProperty("route.volumefrom");
    public static final String ROUTE_VOLUME_TO = propertyUtil.getProperty("route.volumeto");
    public static final String ROUTE_WAGON_TYPE = propertyUtil.getProperty("route.wagontype");
    public static final String ROUTE_NUMBER_ORDER = propertyUtil.getProperty("route.numberorder");
    public static final String ROUTE_NAME_CARGO = propertyUtil.getProperty("route.namecargo");
    public static final String ROUTE_KEY_CARGO = propertyUtil.getProperty("route.keycargo");

    // Заголовки файла Вагоны
    public static final String WAGON_NUMBER_WAGON = propertyUtil.getProperty("wagon.numberwagon");
    public static final String WAGON_KEY_STATION_DEPARTURE = propertyUtil.getProperty("wagon.keystationdeparture");
    public static final String WAGON_NAME_STATION_DEPARTURE = propertyUtil.getProperty("wagon.namestationdeparture");
    public static final String WAGON_ROAD_STATION_DEPARTURE = propertyUtil.getProperty("wagon.roadstationdeparture");
    public static final String WAGON_KEY_STATION_DESTINATION = propertyUtil.getProperty("wagon.keystationdestination");
    public static final String WAGON_NAME_STATION_DESTINATION = propertyUtil.getProperty("wagon.namestationdestination");
    public static final String WAGON_ROAD_STATION_DESTINATION = propertyUtil.getProperty("wagon.roadstationdestination");
    public static final String WAGON_DISTANCE = propertyUtil.getProperty("wagon.distance");
    public static final String WAGON_CUSTOMER = propertyUtil.getProperty("wagon.customer");
    public static final String WAGON_NAME_CARGO = propertyUtil.getProperty("wagon.namecargo");
    public static final String WAGON_KEY_CARGO = propertyUtil.getProperty("wagon.keycargo");
    public static final String WAGON_VOLUME = propertyUtil.getProperty("wagon.volume");
    public static final String WAGON_STATUS = propertyUtil.getProperty("wagon.status");

    // Параметры ZooKeeper
    public static final String ZOOKEEPER_HOST = propertyUtil.getProperty("common.zookeeperhost");
    public static final String ZOOKEEPER_SECRET_KEY = propertyUtil.getProperty("common.secretkey");

    // Строка соотношения расстояния ко дням
    public static final String DISTANCE_DAYS = propertyUtil.getProperty("distanceday");
}
