package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.service.GetList;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import com.uraltranscom.dynamicdistributionpark.service.export.WriteToFileExcel;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Класс получения списка маршрутов
 * Implementation for {@link GetList} interface
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

@Service
@Component
public class GetListOfRoutesImpl extends JavaHelperBase implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfRoutesImpl.class);

    // Основаная мапа, куда записываем все маршруты
    private Map<Integer, Route> mapOfRoutes = new HashMap<>();
    private int count;

    // Переменные для работы с файлами
    private File file;
    private FileInputStream fileInputStream;

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    @Autowired
    private WriteToFileExcel writeToFileExcel;

    private GetListOfRoutesImpl() {
    }

    // Заполняем Map вагонами
    // TODO Переписать метод, избавиться от формата жесткого, необходимо и XLSX и XLS
    @Override
    public void fillMap() {
        count = 0;
        mapOfRoutes.clear();
        writeToFileExcel.setFile(null);
        writeToFileExcel.setFile(file);

        // Получаем файл формата xls
        try {
            fileInputStream = new FileInputStream(this.file);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            // Заполняем Map данными
            sheet = xssfWorkbook.getSheetAt(0);
            int i = 0;
            for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow row = sheet.getRow(1);

                String keyOfStationDeparture = null;
                String nameOfStationDeparture = null;
                String roadOfStationDeparture = null;
                String keyOfStationDestination = null;
                String nameOfStationDestination = null;
                String roadOfStationDestination = null;
                String distanceOfWay = null;
                String customer = null;
                int countOrders = 0;
                int volumeFrom = 0;
                int volumeTo = 0;
                String numberOrder = null;
                String nameCargo = null;
                String keyCargo = null;
                String wagonType = null;

                for (int c = 1; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_KEY_STATION_DEPARTURE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_NAME_STATION_DEPARTURE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_ROAD_STATION_DEPARTURE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_KEY_STATION_DESTINATION)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_NAME_STATION_DESTINATION)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_ROAD_STATION_DESTINATION)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_DISTANCE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        String val = Double.toString(xssfRow.getCell(c).getNumericCellValue());
                        double valueDouble = xssfRow.getCell(c).getNumericCellValue();
                        if ((valueDouble - (int) valueDouble) * 1000 == 0) {
                            val = (int) valueDouble + "";
                        }
                        distanceOfWay = val;
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_CUSTOMER)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        customer = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_COUNT_ORDERS)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        countOrders = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_VOLUME_FROM)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volumeFrom = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_VOLUME_TO)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volumeTo = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_WAGON_TYPE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        wagonType = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_NUMBER_ORDER)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        numberOrder = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_NAME_CARGO)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.ROUTE_KEY_CARGO)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                }
                if (wagonType.equals(TYPE_OF_WAGON_KR)) {
                    mapOfRoutes.put(i, new Route(keyOfStationDeparture, nameOfStationDeparture, roadOfStationDeparture, keyOfStationDestination, nameOfStationDestination, roadOfStationDestination, distanceOfWay, customer, countOrders, volumeFrom, volumeTo, numberOrder, nameCargo, keyCargo, wagonType));
                    i++;
                }
            }
            logger.debug("Body route: {}", mapOfRoutes);
            for (Map.Entry<Integer, Route> _map: mapOfRoutes.entrySet()) {
                count = count + _map.getValue().getCountOrders();
            }
            logger.debug("count: {}", count);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
    }

    public Map<Integer, Route> getMapOfRoutes() {
        return mapOfRoutes;
    }

    public void setMapOfRoutes(Map<Integer, Route> mapOfRoutes) {
        this.mapOfRoutes = mapOfRoutes;
    }

    public void setFile(File file) {
        this.file = file;
        fillMap();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
