package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.service.GetDistanceBetweenStations;
import com.uraltranscom.dynamicdistributionpark.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс получения расстояния между станциями
 * Implementation for {@link GetDistanceBetweenStations} interface
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

@Service
@Component
public class GetDistanceBetweenStationsImpl extends ConnectionDB implements GetDistanceBetweenStations {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetDistanceBetweenStationsImpl.class);

    private GetDistanceBetweenStationsImpl() {
    }

    @Override
    public List<Integer> getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination) {

        List<Integer> listResult = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getInt(1));
            }
            logger.debug("Get distance for: {}", keyOfStationDeparture + "_" + keyOfStationDestination + ": " + listResult.get(0));
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        return listResult;
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call getdistancetest(?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        return callableStatement;
    }
}
