package com.uraltranscom.calculaterate.dao.setting;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.settings.SettingLoadUnload;
import com.uraltranscom.calculaterate.model.settings.SettingYield;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GetSettingYieldDAO extends AbstractObjectFactory<List<SettingYield>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingYieldDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_setting.get_setting_yield()";

    @Override
    public List<SettingYield> getObject(Map<String, Object> params) {
        List<SettingYield> listSetting = new ArrayList<>();

        Connection connection = getConnection();
        CallableStatement callableStatement = null;

        try {
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ResultSet resultSe2 = (ResultSet) resultSet.getObject(1);
                while (resultSe2.next()) {
                    int id = resultSe2.getInt(1);
                    int volumeGroup = resultSe2.getInt(2);
                    double yield = resultSe2.getDouble(3);
                    SettingYield settingYield = new SettingYield(id, volumeGroup, yield);
                    listSetting.add(settingYield);
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
