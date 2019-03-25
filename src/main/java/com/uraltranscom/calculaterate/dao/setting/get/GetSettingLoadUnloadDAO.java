package com.uraltranscom.calculaterate.dao.setting.get;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.settings.SettingLoadUnload;
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

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class GetSettingLoadUnloadDAO extends AbstractObjectFactory<List<SettingLoadUnload>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingLoadUnloadDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_setting.get_setting_loading_unloading()";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public List<SettingLoadUnload> getObject(Map<String, Object> params) {
        List<SettingLoadUnload> listSetting = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionDB.getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ResultSet resultSe2 = (ResultSet) resultSet.getObject(1);
                while (resultSe2.next()) {
                    int num = resultSe2.getInt(1);
                    int type =  resultSe2.getInt(2);
                    String name = resultSe2.getString(3);
                    int value = resultSe2.getInt(4);
                    SettingLoadUnload settingLoadUnload = new SettingLoadUnload(num, name, value);
                    listSetting.add(settingLoadUnload);
                }
            }
            logger.debug("Get info for: {}", params + ": " + listSetting);
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Rollback transaction!");
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

        return listSetting;
    }
}
