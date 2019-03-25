package com.uraltranscom.calculaterate.dao.setting.add;

import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Map;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class InsertSettingReturnExceptionsDAO {
    private static Logger logger = LoggerFactory.getLogger(InsertSettingReturnExceptionsDAO.class);
    private static final String SQL_CALL_NAME = " { call test_setting.insert_setting_return_exception(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    public Object insertObject(Map<String, Object> params) {
        String conflictCode;
        String conflictType;
        String conflictMessage;
        Conflict conflict = null;

        Connection connection = null;
        CallableStatement callableStatement;

        try {
            connection = connectionDB.getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            for (int i = 1; i < params.size() + 1; i++) {
                if (params.get("param" + i) instanceof String[]) {
                    Array namesDepartment = connection.createArrayOf("text", (String[])params.get("param" + i));
                    callableStatement.setArray(i, namesDepartment);
                } else if (params.get("param" + i) instanceof Integer[]) {
                    Array idsDepartment = connection.createArrayOf("integer", (Integer[])params.get("param" + i));
                    callableStatement.setArray(i, idsDepartment);
                } else {
                    callableStatement.setObject(i, params.get("param" + i));
                }
            }
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                conflictCode = (String) resultSet.getObject(1);
                if (conflictCode != null) {
                    conflictType = (String) resultSet.getObject(2);
                    conflictMessage = resultSet.getString(3);
                    conflict = new Conflict(conflictCode, conflictType, conflictMessage);
                } else {
                    connection.commit();
                }
            }
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
            } catch (SQLException e) {
                logger.debug("Error close connection!");
            }
        }
        return conflict;
    }
}
