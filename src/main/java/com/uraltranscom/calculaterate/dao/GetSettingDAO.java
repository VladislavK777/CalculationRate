package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.model.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Component
public class GetSettingDAO extends AbstractObjectFactory<Map<String, List<Setting>>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_distance.get_setting()";

    public GetSettingDAO() {
    }

    @Override
    public Map<String, List<Setting>> getObject(Map<String, Object> params) {
        Map<String, List<Setting>> mapSetting = new HashMap<>();

        try (CallableStatement callableStatement = createCallableStatement(getConnection(), params);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                ResultSet resultSe2 = (ResultSet) resultSet.getObject(1);
                while (resultSe2.next()) {
                    int num = resultSe2.getInt(1);
                    String idRoad = resultSe2.getString(2);
                    String shortNameRoad = resultSe2.getString(3);
                    String idStationString = resultSe2.getString(4);
                    String volumeGroupsString = resultSe2.getString(5);
                    String idStationReturn = resultSe2.getString(6);
                    String nameStationReturn = resultSe2.getString(7);
                    Setting setting = new Setting(num, idRoad, shortNameRoad, idStationString, volumeGroupsString, idStationReturn, nameStationReturn);
                    if (mapSetting.containsKey(shortNameRoad)) {
                        List<Setting> list = mapSetting.get(shortNameRoad);
                        list.add(setting);
                        mapSetting.put(shortNameRoad, list);
                    } else {
                        List<Setting> list = new ArrayList<>();
                        list.add(setting);
                        mapSetting.put(shortNameRoad, list);
                    }
                }
            }
            logger.debug("Get info for: {}", params + ": " + mapSetting);
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
        }

        return mapSetting;
    }

    // TODO перенести в другое место
    private static CallableStatement createCallableStatement(Connection connection, Map<String, Object> params) throws SQLException {
        connection.setAutoCommit(false);
        CallableStatement callableStatement = connection.prepareCall(SQL_CALL_NAME);
        /*for (int i = 1; i < params.size() + 1; i++) {
            callableStatement.setObject(i, params.get("param" + i));
        }*/
        return callableStatement;
    }
}
