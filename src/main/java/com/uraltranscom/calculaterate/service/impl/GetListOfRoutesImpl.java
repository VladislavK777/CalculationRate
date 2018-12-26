package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import com.uraltranscom.calculaterate.service.export.WriteToFileExcel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
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
            ZipSecureFile.setMinInflateRatio(-1.0d);
            xssfWorkbook = new XSSFWorkbook(this.file);

            // Заполняем Map данными
            int i = 0;
            sheet = xssfWorkbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(1);
            for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow xssfRow = sheet.getRow(j);

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
                    String headerCell = row.getCell(c).getStringCellValue().trim();
                    XSSFCell cell = xssfRow.getCell(c);

                    if (headerCell.equals(JavaHelperBase.ROUTE_KEY_STATION_DEPARTURE)) {
                        keyOfStationDeparture = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_NAME_STATION_DEPARTURE)) {
                        nameOfStationDeparture = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_ROAD_STATION_DEPARTURE)) {
                        roadOfStationDeparture = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_KEY_STATION_DESTINATION)) {
                        keyOfStationDestination = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_NAME_STATION_DESTINATION)) {
                        nameOfStationDestination = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_ROAD_STATION_DESTINATION)) {
                        roadOfStationDestination = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_DISTANCE)) {
                        String val = Double.toString(cell.getNumericCellValue());
                        double valueDouble = cell.getNumericCellValue();
                        if ((valueDouble - (int) valueDouble) * 1000 == 0) {
                            val = (int) valueDouble + "";
                        }
                        distanceOfWay = val;
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_CUSTOMER)) {
                        customer = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_COUNT_ORDERS)) {
                        countOrders = (int) cell.getNumericCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_VOLUME_FROM)) {
                        volumeFrom = (int) cell.getNumericCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_VOLUME_TO)) {
                        volumeTo = (int) cell.getNumericCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_WAGON_TYPE)) {
                        wagonType = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_NUMBER_ORDER)) {
                        numberOrder = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_NAME_CARGO)) {
                        nameCargo = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.ROUTE_KEY_CARGO)) {
                        keyCargo = cell.getStringCellValue();
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
        } catch (InvalidFormatException e) {
            e.printStackTrace();
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
