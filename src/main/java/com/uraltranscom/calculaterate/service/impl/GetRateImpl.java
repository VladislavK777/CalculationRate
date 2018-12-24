package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.service.GetRate;
import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Класс получения ставки
 * Implementation for {@link GetRate} interface
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 * 13.10.2018
 *   1. Версия 2.0
 *
 */

@Service
@Component
public class GetRateImpl extends ConnectionDB implements GetRate {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetRateImpl.class);

    private GetRateImpl() {
    }

    @Override
    public Object getRate(String keyOfStationDeparture, String keyOfStationDestination, int cargoType) {

        Object rate = null;

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination, cargoType);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                if (resultSet.getObject(1) == null) {
                    rate = null;
                } else {
                    rate = Math.round((resultSet.getDouble(1)) * 100) / 100.00d;
                }
            }
            logger.debug("Get rate: {}", keyOfStationDeparture + " " + keyOfStationDestination + " " + cargoType + ": " + rate);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        return rate;
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination, int cargoType) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call getrate(?,?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        callableStatement.setInt(3, cargoType);
        return callableStatement;
    }
}
