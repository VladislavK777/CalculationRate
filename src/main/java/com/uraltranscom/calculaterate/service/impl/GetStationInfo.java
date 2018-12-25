package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Country;
import com.uraltranscom.calculaterate.model.RoadStation;
import com.uraltranscom.calculaterate.model.Station;
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
import java.util.stream.Collectors;

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

    public static Station getStationInfo(String idStation) {

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
        List<String> stationInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());
        Station station = new Station(stationInfo.get(0), stationInfo.get(1),
                new RoadStation(stationInfo.get(2), stationInfo.get(3), stationInfo.get(4),
                        new Country(
                                stationInfo.get(5),
                                stationInfo.get(6)
                        )
                )
        );
        return station;
    }

    private static CallableStatement createCallableStatement(Connection connection, String idStation) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call  test_distance.get_station_info(?) } ");
        callableStatement.setString(1, idStation);
        return callableStatement;
    }
}
