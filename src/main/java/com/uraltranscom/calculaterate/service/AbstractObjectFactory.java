package com.uraltranscom.calculaterate.service;

import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;

import java.util.Map;

/**
 * @author Vladislav Klochkov
 * @create 2018-12-25
 */

public abstract class AbstractObjectFactory<T> implements ObjectFactory {
    private ConnectionDB connection = new ConnectionDB();

    @Override
    public abstract T getObject(Map<String, Object> params);

    public ConnectionDB getConnection() {
        return connection;
    }
}
