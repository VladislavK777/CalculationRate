package com.uraltranscom.calculaterate.dao.setting;

import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UpdateSettingReturnExcetpionsDAO extends ConnectionDB {
    private static Logger logger = LoggerFactory.getLogger(UpdateSettingReturnExcetpionsDAO.class);
    private static final String SQL_CALL_NAME = " { call test_setting.update_setting_return_exception(?,?,?,?,?,?,?,?,?,?,?,?,?) } ";

    public void updateObject(Map<String, Object> params) {
        Connection connection;
        CallableStatement callableStatement = null;

        try {
            connection = getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            for (int i = 1; i < params.size() + 1; i++) {
                callableStatement.setObject(i, params.get("param" + i));
            }
            callableStatement.executeQuery();
            connection.commit();
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (SQLException e) {
                logger.debug("Error close connection!");
            }
        }
    }
}
