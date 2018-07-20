package com.uraltranscom.dynamicdistributionpark.util.ConnectUtil;

import javax.sql.DataSource;

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
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource dataSource) {
        ConnectionDB.dataSource = dataSource;
    }
}
