package com.uraltranscom.calculaterate.service.impl;


import com.uraltranscom.calculaterate.model.Country;
import com.uraltranscom.calculaterate.model.RoadStation;
import com.uraltranscom.calculaterate.model.Station;
import com.uraltranscom.calculaterate.model.Tariff;
import com.uraltranscom.calculaterate.service.AbstractObjectFactory;
import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
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
import java.util.stream.Collectors;

/**
 *
 * Класс получения ставки
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 26.07.2018
 *
 * 26.07.2018
 *   1. Версия 1.0
 * 12.10.2018
 *   1. Версия 2.0
 *
 */

@Component
public class GetTariffImpl extends AbstractObjectFactory<Tariff> {
    private static Logger logger = LoggerFactory.getLogger(GetTariffImpl.class);
    private static final String SQL_CALL_NAME = " { call  test_distance.get_tariff2(?,?,?) } ";

    private GetTariffImpl() {
    }

    @Override
    public Tariff getObject(Map<String, Object> params) {
        List<Object> listResult = new ArrayList<>();

        try (CallableStatement callableStatement = createCallableStatement((Connection) getConnection(), params);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get info for: {}", params + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
        }
        List<Object> tariffInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());

        // TODO переделать
        return new Tariff((Double) tariffInfo.get(0), (boolean) tariffInfo.get(1));
    }

    private static CallableStatement createCallableStatement(Connection connection, Map<String, Object> params) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(SQL_CALL_NAME);
        for (int i = 1; i < params.size() + 1; i++) {
            callableStatement.setObject(i, params.get("param" + i));
        }
        return callableStatement;
    }
}
