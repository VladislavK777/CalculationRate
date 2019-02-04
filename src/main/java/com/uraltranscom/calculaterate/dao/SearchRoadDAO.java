package com.uraltranscom.calculaterate.dao;

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

/**
 * @author Vladislav Klochkov
 * @create 2019-01-07
 */

@Component
@NoArgsConstructor
public class SearchRoadDAO extends AbstractObjectFactory<List<Object>> {
    private static Logger logger = LoggerFactory.getLogger(SearchRoadDAO.class);
    private static final String SQL_CALL_NAME = " { call test_distance.get_road_search(?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public List<Object> getObject(Map<String, Object> params) {
        List<Object> listResult = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionDB.getDataSource().getConnection();
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            for (int i = 1; i < params.size() + 1; i++) {
                callableStatement.setObject(i, params.get("param" + i));
            }

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get info for: {}", params + ": " + listResult);
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

        return listResult;
    }
}
