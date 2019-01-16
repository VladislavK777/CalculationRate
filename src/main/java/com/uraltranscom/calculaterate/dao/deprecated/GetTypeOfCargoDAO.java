package com.uraltranscom.calculaterate.dao.deprecated;

/**
 *
 * Класс получения класса груза
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
public class GetTypeOfCargoDAO extends AbstractObjectFactory<Cargo> {
    private static Logger logger = LoggerFactory.getLogger(GetTypeOfCargoDAO.class);
    private static final String SQL_CALL_NAME = " { call test_distance.get_cargo_type2(?) } ";

    public GetTypeOfCargoDAO() {
    }

    @Override
    public Cargo getObject(Map<String, Object> params) {
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
        List<String> cargoInfo = listResult.stream().map(String::valueOf).collect(Collectors.toList());
        // TODO: 2018-12-25 Попробовать в стрим
        return new Cargo(cargoInfo.get(0), cargoInfo.get(1), cargoInfo.get(2));
    }

    // TODO перенести в другое место
    private static CallableStatement createCallableStatement(Connection connection, Map<String, Object> params) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(SQL_CALL_NAME);
        for (int i = 1; i < params.size() + 1; i++) {
            callableStatement.setObject(i, params.get("param" + i));
        }
        return callableStatement;
    }
}
*/
