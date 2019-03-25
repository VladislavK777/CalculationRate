package com.uraltranscom.calculaterate.util.connect;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 19.07.2018
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
