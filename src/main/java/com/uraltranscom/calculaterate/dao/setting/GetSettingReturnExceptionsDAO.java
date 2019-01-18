package com.uraltranscom.calculaterate.dao.setting;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.Cargo;
import com.uraltranscom.calculaterate.model.Road;
import com.uraltranscom.calculaterate.model.Station;
import com.uraltranscom.calculaterate.model.settings.SettingReturnExceptions;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class GetSettingReturnExceptionsDAO extends AbstractObjectFactory<Map<String, List<SettingReturnExceptions>>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingReturnExceptionsDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_setting.get_setting_return_exception()";

    @Override
    public Map<String, List<SettingReturnExceptions>> getObject(Map<String, Object> params) {
        TreeMap<String, List<SettingReturnExceptions>> mapSetting = new TreeMap<>();

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
                    int num = resultSe2.getInt(2);
                    int idRoad = resultSe2.getInt(3);
                    String shortNameRoad = resultSe2.getString(4);
                    String idStationString = resultSe2.getString(5);
                    String volumeGroupsString = resultSe2.getString(6);
                    String idStationFrom = resultSe2.getString(7);
                    String nameStationFrom = resultSe2.getString(8);
                    String idStationTo = resultSe2.getString(9);
                    String nameStationTo = resultSe2.getString(10);
                    String idCargo = resultSe2.getString(11);
                    String nameCargo = resultSe2.getString(12);
                    String typeCargoString = resultSe2.getString(13);
                    String routeType = resultSe2.getString(14);
                    int distance = resultSe2.getInt(15);
                    int countDays = resultSe2.getInt(16);
                    double rate = resultSe2.getDouble(17);
                    double tariff = resultSe2.getDouble(18);
                    SettingReturnExceptions settingReturnExceptions = new SettingReturnExceptions(
                            id,
                            num,
                            new Road(idRoad, shortNameRoad),
                            idStationString,
                            volumeGroupsString,
                            new Station(idStationFrom, nameStationFrom, null),
                            new Station(idStationTo, nameStationTo, null),
                            new Cargo(idCargo, nameCargo),
                            typeCargoString,
                            routeType,
                            distance,
                            countDays,
                            rate,
                            tariff);
                    if (mapSetting.containsKey(shortNameRoad)) {
                        List<SettingReturnExceptions> list = mapSetting.get(shortNameRoad);
                        list.add(settingReturnExceptions);
                        mapSetting.put(shortNameRoad, list);
                    } else {
                        List<SettingReturnExceptions> list = new ArrayList<>();
                        list.add(settingReturnExceptions);
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
