package com.uraltranscom.calculaterate.util;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 21.02.2019
 */
public class WrapperArrayToString {
    public static String wrapperArray(Object[] obj) {
        if (obj != null) {
            String[] str = (String[]) obj;
            if (str.length > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : str) {
                    stringBuilder.append(s).append(",");
                }
                stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
                return stringBuilder.toString();
            }
            return null;
        }
        return null;
    }
}
