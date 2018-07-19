package com.uraltranscom.dynamicdistributionpark.service.additional;

import com.uraltranscom.dynamicdistributionpark.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Класс расчета суточного пробега в зависимости от расстонояния
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class PrepareDistanceOfDay {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(PrepareDistanceOfDay.class);
    private static float distanceOfDay;

    public static Float getDistanceOfDay(int distance) {
        PropertyUtil propertyUtil = new PropertyUtil();
        String stringDistanceOfDay = propertyUtil.getProperty("distanceday");
        String paramSplit [] = stringDistanceOfDay.split(";");
        for (String s : paramSplit) {
            String paramDistSplit [] = s.split("_");
            if (Integer.parseInt(paramDistSplit[0]) <= distance && distance < Integer.parseInt(paramDistSplit[1])) {
                distanceOfDay = Float.parseFloat(paramDistSplit[2]) * 1f;
            }
        }
        return distanceOfDay;
    }

}
