package com.uraltranscom.calculaterate.dao;

import java.util.Map;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 2018-12-25
 */

public interface ObjectFactory {
    Object getObject(Map<String, Object> params);
}
