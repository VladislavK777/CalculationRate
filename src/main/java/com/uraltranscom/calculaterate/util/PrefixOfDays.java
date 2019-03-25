package com.uraltranscom.calculaterate.util;

import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 19.07.2018
 */

public class PrefixOfDays extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(PrefixOfDays.class);

    private static String finishPrefix = null;

    public static String parsePrefixOfDays(int numberOfDays) {
        String numberOfDaysToString = String.valueOf(numberOfDays);
        if (numberOfDaysToString.length() > 1) {
            numberOfDays = Integer.parseInt(numberOfDaysToString.substring(1));
        }

        switch (numberOfDays) {
            case 1:
                finishPrefix = PREFIX_ONE_DAY;
                break;
            case 2:
                finishPrefix = PREFIX_2_4_DAYS;
                break;
            case 3:
                finishPrefix = PREFIX_2_4_DAYS;
                break;
            case 4:
                finishPrefix = PREFIX_2_4_DAYS;
                break;
            case 5:
                finishPrefix = PREFIX_5_10_DAYS;
                break;
            case 6:
                finishPrefix = PREFIX_5_10_DAYS;
                break;
            case 7:
                finishPrefix = PREFIX_5_10_DAYS;
                break;
            case 8:
                finishPrefix = PREFIX_5_10_DAYS;
                break;
            case 9:
                finishPrefix = PREFIX_5_10_DAYS;
                break;
            case 0:
                finishPrefix = PREFIX_5_10_DAYS;
                break;
        }
        return finishPrefix;
    }
}
