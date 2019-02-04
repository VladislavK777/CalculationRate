package com.uraltranscom.calculaterate.dao.setting;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.settings.SettingBorderDistance;
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
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class GetSettingBorderDistanceDAO extends AbstractObjectFactory<List<SettingBorderDistance>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingBorderDistanceDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_setting.get_setting_border_distance()";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public List<SettingBorderDistance> getObject(Map<String, Object> params) {
        List<SettingBorderDistance> listSetting = new ArrayList<>();

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
                    int distanceFrom = resultSe2.getInt(2);
                    int distanceTo = resultSe2.getInt(3);
                    int coefficient = resultSe2.getInt(4);
                    SettingBorderDistance settingBorderDistance = new SettingBorderDistance(
                            id,
                            distanceFrom,
                            distanceTo,
                            coefficient);
                    listSetting.add(settingBorderDistance);
                }
            }
            logger.debug("Get info for: {}", params + ": " + listSetting);
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

        return listSetting;
    }
}
