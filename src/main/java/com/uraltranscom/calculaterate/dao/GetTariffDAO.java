package com.uraltranscom.calculaterate.dao;


import com.uraltranscom.calculaterate.model.Tariff;
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
public class GetTariffDAO extends AbstractObjectFactory<Tariff> {
    private static Logger logger = LoggerFactory.getLogger(GetTariffDAO.class);
    private static final String SQL_CALL_NAME = " { call  test_tariff.get_tariff2(?,?,?) } ";

    private GetTariffDAO() {
    }

    @Override
    public Tariff getObject(Map<String, Object> params) {
        List<Object> listResult = new ArrayList<>();

        try (CallableStatement callableStatement = createCallableStatement(getConnection(), params);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get info for: {}", params + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
        }

        // TODO переделать
        return new Tariff((float)listResult.get(0), (float)listResult.get(1));
    }

    private static CallableStatement createCallableStatement(Connection connection, Map<String, Object> params) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(SQL_CALL_NAME);
        for (int i = 1; i < params.size() + 1; i++) {
            callableStatement.setObject(i, params.get("param" + i));
        }
        return callableStatement;
    }
}
