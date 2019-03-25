package com.uraltranscom.calculaterate.dao.deprecated;


/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 26.07.2018
 */

/*
@Component
public class GetTariffDAO extends AbstractObjectFactory<Tariff> {
    private static Logger logger = LoggerFactory.getLogger(GetTariffDAO.class);
    private static final String SQL_CALL_NAME = " { call  test_tariff.get_tariff2(?,?,?) } ";

    public GetTariffDAO() {
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
*/
