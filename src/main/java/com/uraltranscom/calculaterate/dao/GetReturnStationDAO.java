package com.uraltranscom.calculaterate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 30.12.2018
 */
public class GetReturnStationDAO extends AbstractObjectFactory<String> {
    private static Logger logger = LoggerFactory.getLogger(GetReturnStationDAO.class);
    private static final String SQL_CALL_NAME = " { call test_distance.get_return_station(?,?,?,?) } ";

    @Override
    public String getObject(Map<String, Object> params) {
        List<Object> listResult = new ArrayList<>();

        try (CallableStatement callableStatement = createCallableStatement(getConnection(), params);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get info for: {}", params + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
        }
        // TODO: 2018-12-25 Попробовать в стрим
        return String.valueOf(listResult.get(0));
    }

    // TODO перенести в другое место
    private static CallableStatement createCallableStatement(Connection connection, Map<String, Object> params) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(SQL_CALL_NAME);
        for (int i = 1; i < params.size() + 1; i++) {
            callableStatement.setObject(i, params.get("param" + i));
        }
        return callableStatement;
    }
}
