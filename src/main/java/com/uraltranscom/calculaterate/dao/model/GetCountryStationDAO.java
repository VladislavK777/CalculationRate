package com.uraltranscom.calculaterate.dao.model;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.station.Country;
import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 26.12.2018
 */


@Component
@NoArgsConstructor
public class GetCountryStationDAO extends AbstractObjectFactory<Country> {
    private static Logger logger = LoggerFactory.getLogger(GetCountryStationDAO.class);
    private static final String SQL_CALL_NAME = " { call  test_distance.get_station_info(?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public Country getObject(Map<String, Object> params) {
        List<Object> listResult = new ArrayList<>();
        Country country = null;

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionDB.getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            for (int i = 1; i < params.size() + 1; i++) {
                callableStatement.setObject(i, params.get("param" + i));
            }
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            List<String> stationInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());
            country = new Country(stationInfo.get(5), stationInfo.get(6));
            logger.debug("Get info for: {}", params + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
            try {
                connection.rollback();
                logger.info("Rollback transaction!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.debug("Error close connection!");
            }
        }
        return country;
    }
}

