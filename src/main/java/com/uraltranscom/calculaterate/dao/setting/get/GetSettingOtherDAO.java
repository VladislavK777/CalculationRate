package com.uraltranscom.calculaterate.dao.setting.get;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.settings.SettingOther;
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
 * @date 06.02.2019
 */

@Component
@NoArgsConstructor
public class GetSettingOtherDAO extends AbstractObjectFactory<List<SettingOther>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingOtherDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_setting.get_setting_other()";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public List<SettingOther> getObject(Map<String, Object> params) {
        List<SettingOther> listSetting = new ArrayList<>();

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
                    int id = resultSe2.getInt(1);
                    String name =  resultSe2.getString(2);
                    String volume = resultSe2.getString(3);
                    double value = Math.round(resultSe2.getDouble(4) * 100) / 100.00d;
                    SettingOther settingOther = new SettingOther(id, name, volume, value);
                    listSetting.add(settingOther);
                }
            }
            logger.debug("Get info for: {}", params + ": " + listSetting);
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

        return listSetting;
    }
}
