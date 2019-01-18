package com.uraltranscom.calculaterate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 17.01.2019
 */
public class MainTest {
    public static void main(String[] args) {
        String s = "МОСК (13 Московская ж.д.)";
        String pattern = "[0-9\\+]";
        String number = s.replaceAll("[^0-9\\+]", "");
        // Создание Pattern объекта
        Pattern r = Pattern.compile(pattern);
        System.out.println(number);
        // Создание matcher объекта
        Matcher m = r.matcher(s);
        if (m.find( )) {
            System.out.println("Найдено значение: " + m.group(0));
        }else {
            System.out.println("НЕ СОВПАДАЕТ");
        }
    }
}
