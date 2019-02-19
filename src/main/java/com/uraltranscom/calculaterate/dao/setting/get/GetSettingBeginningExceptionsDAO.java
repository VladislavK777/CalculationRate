package com.uraltranscom.calculaterate.dao.setting.get;

import com.uraltranscom.calculaterate.dao.AbstractObjectFactory;
import com.uraltranscom.calculaterate.model.route.Cargo;
import com.uraltranscom.calculaterate.model.station.Station;
import com.uraltranscom.calculaterate.model.settings.SettingReturnExceptions;
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
public class GetSettingBeginningExceptionsDAO extends AbstractObjectFactory<Map<String, List<SettingReturnExceptions>>> {
    private static Logger logger = LoggerFactory.getLogger(GetSettingBeginningExceptionsDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_setting.get_setting_beginning_exception()";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public Map<String, List<SettingReturnExceptions>> getObject(Map<String, Object> params) {
        TreeMap<String, List<SettingReturnExceptions>> mapSetting = new TreeMap<>();

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
                    String namesRoad = resultSe2.getString(3);
                    String idsStationString = resultSe2.getString(4);
                    String volumeGroupsString = resultSe2.getString(5);
                    String idStationFrom = resultSe2.getString(6);
                    String nameStationFrom = resultSe2.getString(7);
                    String idStationTo = resultSe2.getString(8);
                    String nameStationTo = resultSe2.getString(9);
                    String idCargo = resultSe2.getString(10);
                    String nameCargo = resultSe2.getString(11);
                    String typeCargoString = resultSe2.getString(12);
                    String routeType = resultSe2.getString(13);
                    int distance = resultSe2.getInt(14);
                    int countDays = resultSe2.getInt(15);
                    double rate = resultSe2.getDouble(16);
                    double tariff = resultSe2.getDouble(17);
                    SettingReturnExceptions settingReturnExceptions = new SettingReturnExceptions(
                            id,
                            num,
                            namesRoad,
                            idsStationString,
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
                    if (mapSetting.containsKey(namesRoad)) {
                        List<SettingReturnExceptions> list = mapSetting.get(namesRoad);
                        list.add(settingReturnExceptions);
                        mapSetting.put(namesRoad, list);
                    } else {
                        List<SettingReturnExceptions> list = new ArrayList<>();
                        list.add(settingReturnExceptions);
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
