package com.uraltranscom.calculaterate.dao.setting.update;

import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class UpdateSettingReturnStationsDAO {
    private static Logger logger = LoggerFactory.getLogger(UpdateSettingReturnStationsDAO.class);
    private static final String SQL_CALL_NAME = " { call test_setting.update_setting_return_stations(?,?,?,?,?,?,?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    public void updateObject(Map<String, Object> params) {
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
            callableStatement.executeQuery();
            connection.commit();
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
    }
}
