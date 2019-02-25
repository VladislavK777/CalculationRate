package com.uraltranscom.calculaterate.dao.setting.clone;

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
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@Component
@NoArgsConstructor
public class CloneBeginningExceptionDAO extends AbstractObjectFactory<SettingReturnExceptions> {
    private static Logger logger = LoggerFactory.getLogger(CloneBeginningExceptionDAO.class);
    private static final String SQL_CALL_NAME = " { call test_setting.clone_setting_beginning_exception(?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public SettingReturnExceptions getObject(Map<String, Object> params) {
        Connection connection = null;
        CallableStatement callableStatement;
        SettingReturnExceptions settingReturnExceptions = null;

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
                    int num = resultSe2.getInt(2);
                    String namesRoad = resultSe2.getString(3);
                    String idsStationString = resultSe2.getString(4);
                    Integer[] idsDepartment = resultSe2.getArray(5) != null ? (Integer[]) resultSe2.getArray(5).getArray() : null;
                    String[] namesDepartment = resultSe2.getArray(6) != null ? (String[])resultSe2.getArray(6).getArray() : null;
                    String volumeGroupsString = resultSe2.getString(7);
                    String idStationFrom = resultSe2.getString(8);
                    String nameStationFrom = resultSe2.getString(9);
                    String idStationTo = resultSe2.getString(10);
                    String nameStationTo = resultSe2.getString(11);
                    String idCargo = resultSe2.getString(12);
                    String nameCargo = resultSe2.getString(13);
                    String typeCargoString = resultSe2.getString(14);
                    String routeType = resultSe2.getString(15);
                    int distance = resultSe2.getInt(16);
                    int countDays = resultSe2.getInt(17);
                    double rate = resultSe2.getDouble(18);
                    double tariff = resultSe2.getDouble(19);
                    settingReturnExceptions = new SettingReturnExceptions(
                            id,
                            num,
                            namesRoad,
                            idsStationString,
                            idsDepartment,
                            namesDepartment,
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
                }
            }
            logger.info("Get info for clone: {}", params + ": " + settingReturnExceptions);
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
        return settingReturnExceptions;
    }
}
