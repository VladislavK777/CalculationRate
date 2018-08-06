package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.service.CheckExistKeyOfStation;
import com.uraltranscom.dynamicdistributionpark.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 *
 * Класс проверки корректности кода станции
 * Implementation for {@link CheckExistKeyOfStation} interface
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

@Service
@Component
public class CheckExistKeyOfStationImpl extends ConnectionDB implements CheckExistKeyOfStation {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(CheckExistKeyOfStationImpl.class);

    private CheckExistKeyOfStationImpl() {
    }

    public boolean checkExistKey(String keyOfStation) {

        Boolean isExist = false;

        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(connection, keyOfStation);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                if (!resultSet.wasNull()) {
                    isExist = true;
                }
            }
            logger.debug("Проверяем станцию: {}", keyOfStation);
        } catch (SQLException ex) {
            logger.error("Ошибка запроса: {}", ex.getMessage());
        }
        return isExist;
    }

    private PreparedStatement createPreparedStatement(Connection connection, String keyOfStation) throws SQLException {
        String sql = "select distinct 1 from distances_new where (station_id1 = ? or station_id2 = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyOfStation);
        preparedStatement.setString(2, keyOfStation);
        return preparedStatement;
    }
}
