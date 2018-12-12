package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.service.InsertRate;
import com.uraltranscom.dynamicdistributionpark.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * Класс добавления ставки в БД
 * Implementation for {@link InsertRate} interface
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
public class InsertRateImpl extends ConnectionDB implements InsertRate {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(InsertRateImpl.class);

    @Override
    public void insertRate(String keyOfStationDeparture, String keyOfStationDestination, int cargoType, double rate) {
        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination, cargoType, rate)) {
            callableStatement.executeQuery();
            logger.debug("Insert rate: {}", keyOfStationDeparture + " " + keyOfStationDestination + ": " + rate);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination, int cargoType, double rateOrTariff) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call inserttorate(?,?,?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        callableStatement.setInt(3, cargoType);
        callableStatement.setDouble(4, rateOrTariff);
        return callableStatement;
    }
}
