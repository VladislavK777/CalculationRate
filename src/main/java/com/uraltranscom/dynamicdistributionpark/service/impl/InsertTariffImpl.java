package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.service.InsertRateOrTariff;
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
 * Класс добавления тарифа в БД
 * Implementation for {@link InsertRateOrTariff} interface
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
@Component
public class InsertTariffImpl extends ConnectionDB implements InsertRateOrTariff {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(InsertTariffImpl.class);

    @Override
    public void insertRateOfTariff(String keyOfStationDeparture, String keyOfStationDestination, int cargoType, double rateOrTariff) {
        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination, cargoType, rateOrTariff)) {
            callableStatement.executeQuery();
            logger.debug("Insert tariff: {}", keyOfStationDeparture + " " + keyOfStationDestination + ": " + rateOrTariff);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination, int cargoType, double rateOrTariff) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call inserttotariff(?,?,?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        callableStatement.setInt(3, cargoType);
        callableStatement.setDouble(4, rateOrTariff);
        return callableStatement;
    }
}
