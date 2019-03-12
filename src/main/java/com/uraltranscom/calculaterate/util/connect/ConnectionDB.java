package com.uraltranscom.calculaterate.util.connect;

import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * Класс получения DataSource
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class ConnectionDB {
    private static HikariDataSource dataSource;

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(HikariDataSource dataSource) {
        ConnectionDB.dataSource = dataSource;
    }
}
