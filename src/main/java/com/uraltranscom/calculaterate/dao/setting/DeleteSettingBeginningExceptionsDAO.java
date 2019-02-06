package com.uraltranscom.calculaterate.dao.setting;

import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class DeleteSettingBeginningExceptionsDAO {
    private static Logger logger = LoggerFactory.getLogger(DeleteSettingBeginningExceptionsDAO.class);
    private static final String SQL_CALL_NAME = " { call test_setting.delete_setting_beginning_exception(?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    public void deleteObject(Map<String, Object> params) {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionDB.getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            for (int i = 1; i < params.size() + 1; i++) {
                logger.info("params: {}", params);
                callableStatement.setObject(i, params.get("param" + i));
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
