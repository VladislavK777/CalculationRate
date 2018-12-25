package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Cargo;
import com.uraltranscom.calculaterate.service.GetTypeOfCargo;
import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Класс получения класса груза
 * Implementation for {@link GetTypeOfCargo} interface
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
public class GetTypeOfCargoImpl extends ConnectionDB {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetTypeOfCargoImpl.class);

    private GetTypeOfCargoImpl() {
    }

    public static Cargo getTypeOfCargo(String idCargo) {

        List<String> listResult = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, idCargo);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getString(1));
            }
            logger.debug("Get type of cargo: {}", idCargo + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        List<String> cargoInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());
        Cargo cargo = new Cargo(cargoInfo.get(0), cargoInfo.get(1), cargoInfo.get(2));
        return cargo;
    }

    private static CallableStatement createCallableStatement(Connection connection, String idCargo) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call getclassofcargo2(?) } ");
        callableStatement.setString(1, idCargo);
        return callableStatement;
    }
}
