package com.uraltranscom.calculaterate.service;

import java.util.Map;

/**
 * @author Vladislav Klochkov
 * @create 2018-12-25
 */

public interface ObjectFactory {
    Object getObject(Map<String, Object> params);
}
