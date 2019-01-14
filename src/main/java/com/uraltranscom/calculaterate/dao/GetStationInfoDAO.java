package com.uraltranscom.calculaterate.dao;

/**
 *
 * Класс получения станции
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.12.2018
 *
 * 26.12.2018
 *   1. Версия 1.0
 *
 */

/*
@Component
public class GetStationInfoDAO extends AbstractObjectFactory<Station> {
    private static Logger logger = LoggerFactory.getLogger(GetStationInfoDAO.class);
    private static final String SQL_CALL_NAME = " { call  test_distance.get_station_info(?) } ";

    public GetStationInfoDAO() {
    }

    @Override
    public Station getObject(Map<String, Object> params) {
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
        List<String> stationInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());

        // TODO переделать
        return new Station(stationInfo.get(0), stationInfo.get(1),
                new Road(stationInfo.get(2), stationInfo.get(3), stationInfo.get(4),
                        new Country(
                                stationInfo.get(5),
                                stationInfo.get(6)
                        )
                )
        );
    }

    private static CallableStatement createCallableStatement(Connection connection, Map<String, Object> params) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(SQL_CALL_NAME);
        for (int i = 1; i < params.size() + 1; i++) {
            callableStatement.setObject(i, params.get("param" + i));
        }
        return callableStatement;
    }
}
*/
