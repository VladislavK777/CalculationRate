package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.service.GetRateOrTariff;
import com.uraltranscom.dynamicdistributionpark.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Класс получения ставки
 * Implementation for {@link GetRateOrTariff} interface
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 *
 */

@Service
public class GetTariffImpl extends ConnectionDB implements GetRateOrTariff {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetTariffImpl.class);

    private GetTariffImpl() {
    }

    @Override
    public Object getRateOrTariff(String keyOfStationDeparture, String keyOfStationDestination, int cargoType) {

        Object tariff = null;

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination, cargoType);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                if (resultSet.getObject(1) == null) {
                    tariff = null;
                } else {
                    tariff = Math.round((resultSet.getDouble(1)) * 100) / 100.00d;
                }
            }
            logger.debug("Get tariff: {}", keyOfStationDeparture + " " + keyOfStationDestination + ": " + tariff);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        return tariff;
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination, int cargoType) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call gettariff(?,?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        callableStatement.setInt(3, cargoType);
        return callableStatement;
    }
}
