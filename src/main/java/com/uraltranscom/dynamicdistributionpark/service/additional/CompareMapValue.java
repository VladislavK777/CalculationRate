package com.uraltranscom.dynamicdistributionpark.service.additional;

import java.util.List;

/**
 *
 * Класс сортировки
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class CompareMapValue implements Comparable {
    public List<Object> wagon;
    public Integer distance;

    public CompareMapValue(List<Object> wagon, Integer distance) {
        this.wagon = wagon;
        this.distance = distance;
    }

    public int compareTo(Object o) {
        if (o instanceof CompareMapValue) {
            final int diff = distance - ((CompareMapValue) o).distance;
            return Integer.compare(diff, 0);
        } else {
            return 0;
        }
    }
}
