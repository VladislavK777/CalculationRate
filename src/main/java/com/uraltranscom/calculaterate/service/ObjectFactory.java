package com.uraltranscom.calculaterate.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

/**
 * @author Vladislav Klochkov
 * @create 2018-12-25
 */

public interface ObjectFactory<T> {
    Object getObject(List<T> params);
}
