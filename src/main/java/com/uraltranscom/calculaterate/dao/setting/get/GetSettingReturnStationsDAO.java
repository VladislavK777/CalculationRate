package com.uraltranscom.calculaterate.dao.setting.get;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class GetSettingReturnStationsDAO extends AbstractObjectFactory<Map<String, List<SettingReturnStations>>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingReturnStationsDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_setting.get_setting_return_station()";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public Map<String, List<SettingReturnStations>> getObject(Map<String, Object> params) {
        TreeMap<String, List<SettingReturnStations>> mapSetting = new TreeMap<>();

        Connection connection = null;
        CallableStatement callableStatement;

        try {
            connection = connectionDB.getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ResultSet resultSe2 = (ResultSet) resultSet.getObject(1);
                while (resultSe2.next()) {
                    int id = resultSe2.getInt(1);
                    String namesRoad = resultSe2.getString(2);
                    String idsStationString = resultSe2.getString(3);
                    Integer[] idsDepartment = resultSe2.getArray(4) != null ? (Integer[]) resultSe2.getArray(4).getArray() : null;
                    String[] namesDepartment = resultSe2.getArray(5) != null ? (String[])resultSe2.getArray(5).getArray() : null;
                    String volumeGroupsString = resultSe2.getString(6);
                    String idStationReturn = resultSe2.getString(7);
                    String nameStationReturn = resultSe2.getString(8);
                    SettingReturnStations settingReturnStations = new SettingReturnStations(id, namesRoad, idsStationString, idsDepartment, namesDepartment, volumeGroupsString, idStationReturn, nameStationReturn);
                    if (mapSetting.containsKey(namesRoad)) {
                        List<SettingReturnStations> list = mapSetting.get(namesRoad);
                        list.add(settingReturnStations);
                        mapSetting.put(namesRoad, list);
                    } else {
                        List<SettingReturnStations> list = new ArrayList<>();
                        list.add(settingReturnStations);
                        mapSetting.put(namesRoad, list);
                    }
                }
            }
            logger.debug("Get info for: {}", params + ": " + mapSetting);
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

        return mapSetting;
    }
}
