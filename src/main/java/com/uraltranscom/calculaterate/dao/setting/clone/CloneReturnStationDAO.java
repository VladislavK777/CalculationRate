package com.uraltranscom.calculaterate.dao.setting.clone;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
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
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class CloneReturnStationDAO extends AbstractObjectFactory<SettingReturnStations> {
    private static Logger logger = LoggerFactory.getLogger(CloneReturnStationDAO.class);
    private static final String SQL_CALL_NAME = " { call test_setting.clone_setting_return_stations(?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public SettingReturnStations getObject(Map<String, Object> params) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        SettingReturnStations settingReturnStations = null;

        try {
            connection = connectionDB.getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            for (int i = 1; i < params.size() + 1; i++) {
                callableStatement.setObject(i, params.get("param" + i));
            }
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ResultSet resultSe2 = (ResultSet) resultSet.getObject(1);
                while (resultSe2.next()) {
                    int id = resultSe2.getInt(1);
                    String namesRoad = resultSe2.getString(2);
                    String idsStationString = resultSe2.getString(3);
                    Integer[] idsDepartment = (Integer[])resultSe2.getArray(4).getArray();
                    String[] namesDepartment = (String[])resultSe2.getArray(5).getArray();
                    String volumeGroupsString = resultSe2.getString(6);
                    String idStationReturn = resultSe2.getString(7);
                    String nameStationReturn = resultSe2.getString(8);
                    settingReturnStations = new SettingReturnStations(id, namesRoad, idsStationString, idsDepartment, namesDepartment, volumeGroupsString, idStationReturn, nameStationReturn);
                }
            }
            logger.debug("Get info for clone: {}", params + ": " + settingReturnStations);
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
        return settingReturnStations;
    }
}
