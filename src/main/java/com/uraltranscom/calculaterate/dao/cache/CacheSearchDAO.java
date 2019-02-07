package com.uraltranscom.calculaterate.dao.cache;

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

/**
 * @author Vladislav Klochkov
 * @create 2019-01-05
 */

@Component
@NoArgsConstructor
public class CacheSearchDAO {
    private static Logger logger = LoggerFactory.getLogger(CacheSearchDAO.class);

    @Autowired
    private ConnectionDB connectionDB;

    public List<Object> getObject(String marker) {
        List<Object> listResult = new ArrayList<>();

        Connection connection = null;
        CallableStatement callableStatement;

        try {
            connection = connectionDB.getDataSource().getConnection();
            callableStatement = connection.prepareCall("select * from test_distance.get_" + marker + "_search()");
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
