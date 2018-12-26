package com.uraltranscom.calculaterate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Класс подготовки мапы с параметрами
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.12.2018
 *
 * 26.12.2018
 *   1. Версия 1.0
 *
 */

public final class PrepareMapParams {
    private static Logger logger = LoggerFactory.getLogger(PrepareMapParams.class);
    private static final String PREFIX_PARAM = "param";

    public static Map<String, Object> prepareMapWithParams(Object... params) {
        Map<String, Object> map = new HashMap<>();
        if (params.length == 0) {
            throw new NullPointerException("Params is empty");
        } else {
            for (int i = 1; i < params.length + 1; i++) {
                logger.debug("Prepare params: {}", params[i - 1]);
                map.put(PREFIX_PARAM + i, params[i - 1]);
            }
        }
        return map;
    }
}
