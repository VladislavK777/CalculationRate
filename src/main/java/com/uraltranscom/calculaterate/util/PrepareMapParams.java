package com.uraltranscom.calculaterate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 26.12.2018
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
