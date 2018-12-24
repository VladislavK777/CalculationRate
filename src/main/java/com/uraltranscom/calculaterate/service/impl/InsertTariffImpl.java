package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.service.InsertTariff;
import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * Класс добавления тарифа в БД
 * Implementation for {@link InsertTariff} interface
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
public class InsertTariffImpl extends ConnectionDB implements InsertTariff {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(InsertTariffImpl.class);

    @Override
    public void insertTariff(String keyOfStationDeparture, String keyOfStationDestination, String keyCargo, double tariff) {
        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination, keyCargo, tariff)) {
            callableStatement.executeQuery();
            logger.debug("Insert tariff: {}", keyOfStationDeparture + " " + keyOfStationDestination + " " + keyCargo + ": " + tariff);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination, String keyCargo, double tariff) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call test_tariff.insert_tariff(?,?,?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        callableStatement.setString(3, keyCargo);
        callableStatement.setDouble(4, tariff);
        return callableStatement;
    }
}
