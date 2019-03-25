package com.uraltranscom.calculaterate.dao.deprecated;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 26.12.2018
 */

/*
@Component
public class GetDistanceBetweenStationsDAO extends AbstractObjectFactory<Distance> {
    private static Logger logger = LoggerFactory.getLogger(GetDistanceBetweenStationsDAO.class);
    private static final String SQL_CALL_NAME = " { call  test_distance.get_root_distance3(?,?,?) } ";

    public GetDistanceBetweenStationsDAO() {
    }

    @Override
    public Distance getObject(Map<String, Object> params) {
        List<Object> listResult = new ArrayList<>();

        try (CallableStatement callableStatement = createCallableStatement((Connection) getConnection(), params);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                listResult.add(resultSet.getObject(1));
            }
            logger.debug("Get for: {}", params + ": " + listResult);
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
        }
        List<String> distanceInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());
        return new Distance(distanceInfo.get(0), distanceInfo.get(1), distanceInfo.get(2), distanceInfo.get(3), distanceInfo.get(4));
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
