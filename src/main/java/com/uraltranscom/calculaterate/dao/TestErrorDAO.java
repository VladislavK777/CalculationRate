package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 26.01.2019
 */

@Component
public class TestErrorDAO extends AbstractObjectFactory<Object> {

    @Override
    public Object getObject(Map<String, Object> params) {
        String conflictCode;
        String conflictType;
        String conflictMessage;
        String station = null;
        Conflict conflict = null;

        Connection connection = getConnection();
        CallableStatement callableStatement = null;

        try {
            callableStatement = connection.prepareCall(" { call test_distance.test_get_station(?) } ");
            for (int i = 1; i < params.size() + 1; i++) {
                callableStatement.setObject(i, params.get("param" + i));
            }
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                station = (String) resultSet.getObject(1);
                if (station == null) {
                    conflictCode = (String) resultSet.getObject(2);
                    conflictType = (String) resultSet.getObject(3);
                    conflictMessage = (String) resultSet.getObject(4);
                    conflict = new Conflict(conflictCode, conflictType, conflictMessage);
                    return conflict;

                }
            }

        } catch (SQLException sqlEx) {
            sqlEx.getMessage();
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                e.getErrorCode();
            }
        }
        return station;
    }
}
