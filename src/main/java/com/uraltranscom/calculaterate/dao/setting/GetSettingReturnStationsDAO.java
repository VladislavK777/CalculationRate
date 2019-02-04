package com.uraltranscom.calculaterate.dao.setting;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.Road;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
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
                    int num = resultSe2.getInt(2);
                    int idRoad = resultSe2.getInt(3);
                    String shortNameRoad = resultSe2.getString(4);
                    String idStationString = resultSe2.getString(5);
                    String volumeGroupsString = resultSe2.getString(6);
                    String idStationReturn = resultSe2.getString(7);
                    String nameStationReturn = resultSe2.getString(8);
                    SettingReturnStations settingReturnStations = new SettingReturnStations(id, num, new Road(idRoad, shortNameRoad), idStationString, volumeGroupsString, idStationReturn, nameStationReturn);
                    if (mapSetting.containsKey(shortNameRoad)) {
                        List<SettingReturnStations> list = mapSetting.get(shortNameRoad);
                        list.add(settingReturnStations);
                        mapSetting.put(shortNameRoad, list);
                    } else {
                        List<SettingReturnStations> list = new ArrayList<>();
                        list.add(settingReturnStations);
                        mapSetting.put(shortNameRoad, list);
                    }
                }
            }
            logger.debug("Get info for: {}", params + ": " + mapSetting);
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

        return mapSetting;
    }
}
