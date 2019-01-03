package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Vladislav Klochkov
 * @create 2018-12-25
 */

@Getter
public abstract class AbstractObjectFactory<T> extends ConnectionDB implements ObjectFactory {
    private Connection connection;

    {
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public abstract T getObject(Map<String, Object> params);
}
