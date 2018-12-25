package com.uraltranscom.calculaterate.service.impl;


import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс получения ставки
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 * 12.10.2018
 *   1. Версия 2.0
 *
 */

@Component
public class GetTariffImpl extends ConnectionDB {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetTariffImpl.class);

    private GetTariffImpl() {
    }

    public List<Object> getObject(int idCountry, int distance, String idCargo) {

        List<Object> listResult = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, idCountry, distance, idCargo);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getDouble(1));
                /**if (resultSet.getObject(1) == null) {
                    tariff = null;
                } else {
                    tariff = Math.round((resultSet.getDouble(1)) * 100) / 100.00d;
                }**/
            }
            logger.debug("Get tariff: {}", idCountry + " " + distance + "_" + idCargo + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        return listResult;
    }

    private CallableStatement createCallableStatement(Connection connection, int countryKey, int distance, String keyCargo) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call test_tariff.get_tariff2(?,?,?) } ");
        callableStatement.setInt(1, countryKey);
        callableStatement.setInt(2, distance);
        callableStatement.setString(3, keyCargo);
        return callableStatement;
    }
}
