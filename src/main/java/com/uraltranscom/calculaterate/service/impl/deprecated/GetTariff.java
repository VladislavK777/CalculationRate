package com.uraltranscom.calculaterate.service.impl.deprecated;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 2019-01-01
 */
/*

@Component
public class GetTariff {
    @Autowired
    protected GetTariffDAO getTariffDAO;

    public List<Object> getTariff(Distance distanceInfo, String idCargo) {
        double tariff = 0.00;
        boolean isLoadingTariffFromDB = false;
        String [] codeCountryArray = distanceInfo.getRouteCountries().split("/");
        List<Integer> listFlag = new ArrayList<>();
        List<Object> result = new ArrayList<>();
        if (codeCountryArray.length == 1) {
            tariff = getTariffDAO.getObject(prepareMapWithParams(
                    Integer.parseInt(codeCountryArray[0]),
                    Integer.parseInt(distanceInfo.getDistance()),
                    idCargo)).getTariff();
        } else if (codeCountryArray.length == 2) {
            List<Integer> distanceList = new ArrayList<>();
            int distanceStart = Integer.parseInt(distanceInfo.getDistanceStart());
            int distanceEnd = Integer.parseInt(distanceInfo.getDistanceEnd());
            distanceList.add(distanceStart);
            distanceList.add(distanceEnd);
            for (int i = 0; i < codeCountryArray.length; i++) {
                Tariff tariffFull = getTariffDAO.getObject(prepareMapWithParams(
                        Integer.parseInt(codeCountryArray[i]),
                        distanceList.get(i),
                        idCargo));
                tariff = tariff + tariffFull.getTariff();
                listFlag.add((int)tariffFull.getFlagDownloadFromDB());
            }
        } else {
            String [] distanceTransitArray;
            List<Integer> distanceList = new ArrayList<>();
            int distanceStart = Integer.parseInt(distanceInfo.getDistanceStart());
            int distanceEnd = Integer.parseInt(distanceInfo.getDistanceEnd());
            distanceList.add(distanceStart);
            if (!distanceInfo.getRouteDistance().isEmpty()) {
                distanceTransitArray = distanceInfo.getRouteDistance().split("/");
                for (String s: distanceTransitArray) {
                    distanceList.add(Integer.parseInt(s));
                }
            }
            distanceList.add(distanceEnd);
            for (int i = 0; i < codeCountryArray.length; i++) {
                Tariff tariffFull = getTariffDAO.getObject(prepareMapWithParams(
                        Integer.parseInt(codeCountryArray[i]),
                        distanceList.get(i),
                        idCargo));
                tariff = tariff + tariffFull.getTariff();
                listFlag.add((int)tariffFull.getFlagDownloadFromDB());
            }
        }
        tariff = (Math.round(tariff * 100) / 100.00d) * (-1);
        isLoadingTariffFromDB = listFlag.contains(1);
        result.add(tariff);
        result.add(isLoadingTariffFromDB);
        return result;
    }
}
*/
