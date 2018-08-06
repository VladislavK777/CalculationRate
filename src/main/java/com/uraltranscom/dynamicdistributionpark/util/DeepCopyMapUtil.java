package com.uraltranscom.dynamicdistributionpark.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 * Класс глубокого копирования Map
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 31.07.2018
 *
 * 31.07.2018
 *   1. Версия 1.0
 *
 */

public class DeepCopyMapUtil<K, V> {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(DeepCopyMapUtil.class);

    public Map<? extends K, ? extends V> copy(Map<? extends K, ? extends V> m) {
        return null;
    }
}
