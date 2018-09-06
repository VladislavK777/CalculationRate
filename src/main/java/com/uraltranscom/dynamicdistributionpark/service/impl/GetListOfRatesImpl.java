package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.RateClass;
import com.uraltranscom.dynamicdistributionpark.service.GetList;
import com.uraltranscom.dynamicdistributionpark.service.additional.JavaHelperBase;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Класс получения списка ставок
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
public class GetListOfRatesImpl implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfRatesImpl.class);

    // Основаная мапа, куда записываем все маршруты
    private Map<Integer, RateClass> mapOfRates = new HashMap<>();

    // Переменные для работы с файлами
    private File file;
    private FileInputStream fileInputStream;

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    private GetListOfRatesImpl() {
    }

    @Override
    public void fillMap() {
        mapOfRates.clear();
        // Получаем файл формата xls
        try {
            fileInputStream = new FileInputStream(this.file);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            // Заполняем Map данными
            sheet = xssfWorkbook.getSheetAt(0);
            int i = 0;
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow row = sheet.getRow(0);

                String nameOfStationDeparture = null;
                String nameOfStationDestination = null;
                String customer = null;
                double rate = 0.00d;
                Date dateLoading = null;
                String nameCargo = null;
                String keyCargo = null;

                for (int c = 0; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.RATE_NAME_STATION_DEPARTURE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.RATE_NAME_STATION_DESTINATION)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.RATE_CUSTOMER)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        customer = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.RATE_NAME_CARGO)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.RATE_KEY_CARGO)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        if (xssfRow.getCell(c).getCellTypeEnum().equals(CellType.NUMERIC)) {
                            keyCargo = String.valueOf(xssfRow.getCell(c).getNumericCellValue());
                        } else {
                            keyCargo = xssfRow.getCell(c).getStringCellValue();
                        }
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.RATE_RATE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        rate = xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.RATE_DATE_LOADING)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        dateLoading = xssfRow.getCell(c).getDateCellValue();
                        dateLoading.setHours(0);
                        dateLoading.setMinutes(0);
                        dateLoading.setSeconds(0);
                        if (dateLoading == null) dateLoading = new Date();
                    }
                }
                if (!mapOfRates.containsValue(new RateClass(nameOfStationDeparture, nameOfStationDestination, customer, nameCargo, keyCargo, rate, dateLoading))) {
                    mapOfRates.put(i, new RateClass(nameOfStationDeparture, nameOfStationDestination, customer, nameCargo, keyCargo, rate, dateLoading));
                    i++;
                }
            }
            logger.debug("Body rates: {}", mapOfRates);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
    }

    public Map<Integer, RateClass> getMapOfRates() {
        return mapOfRates;
    }

    public void setMapOfRates(Map<Integer, RateClass> mapOfRates) {
        this.mapOfRates = mapOfRates;
    }

    public void setFile(File file) {
        this.file = file;
        fillMap();
    }
}
