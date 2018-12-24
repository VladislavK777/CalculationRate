package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 24.12.2018
 */

@Service
@Component
public class GetStationInfo extends ConnectionDB {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetStationInfo.class);

    private GetStationInfo() {
    }

    public static List<Object> getStationInfo(String idStation) {

        List<Object> listResult = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, idStation);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get station info for: {}", idStation + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        return listResult;
    }

    private static CallableStatement createCallableStatement(Connection connection, String idStation) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call  test_distance.get_station_info(?) } ");
        callableStatement.setString(1, idStation);
        return callableStatement;
    }
}
