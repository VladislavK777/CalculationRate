package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.model.cache.CacheStationsSearch;
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
 * @create 2019-01-05
 */

@Component
@NoArgsConstructor
public class SearchStationDAO {
    private static Logger logger = LoggerFactory.getLogger(SearchStationDAO.class);
    private static final String SQL_CALL_NAME = "select * from test_distance.get_station_search()";

    @Autowired
    private ConnectionDB connectionDB;

    public List<Object> getObject() {
        List<Object> listResult = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = connectionDB.getDataSource().getConnection();
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get info for: {}", listResult.size());

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
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
        return listResult;
    }
}
