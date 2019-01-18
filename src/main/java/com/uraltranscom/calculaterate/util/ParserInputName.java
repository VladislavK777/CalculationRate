package com.uraltranscom.calculaterate.util;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-06
 */

public class ParserInputName {
    private static final String PREFIX_SPLIT = " ";

    public static String getId(String input) {
        String[] s = input.split(PREFIX_SPLIT);
        return s[0];
    }

    public static String getIdRoad(String input) {
        return input.replaceAll("[^0-9\\+]", "");
    }
}
