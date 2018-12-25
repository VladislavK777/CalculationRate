package com.uraltranscom.calculaterate.service;

import com.uraltranscom.calculaterate.model.Country;
import com.uraltranscom.calculaterate.model.RoadStation;
import com.uraltranscom.calculaterate.model.Station;
import com.uraltranscom.calculaterate.service.impl.InsertTariffImpl;
import com.uraltranscom.calculaterate.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vladislav Klochkov
 * @create 2018-12-25
 */

public abstract class AbstractObjectFactory<T> extends ConnectionDB implements ObjectFactory<T> {
    private static Logger logger = LoggerFactory.getLogger(AbstractObjectFactory.class);

    @Override
    public T getObject(List<T> params) {
        /*List<Object> listResult = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, idStation);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get station info for: {}", idStation + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {}", sqlEx.getMessage());
        }
        List<String> objectInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());
        T object = new Station(stationInfo.get(0), stationInfo.get(1),
                new RoadStation(stationInfo.get(2), stationInfo.get(3), stationInfo.get(4),
                        new Country(
                                stationInfo.get(5),
                                stationInfo.get(6)
                        )
                )
        );*/
        return null;
    }
}
