package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.RateClass;
import com.uraltranscom.calculaterate.service.GetList;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
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

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    private RateClass rateClass = null;

    private GetListOfRatesImpl() {
    }

    @Override
    public void fillMap() {
        mapOfRates.clear();
        // Получаем файл формата xls
        try {
            xssfWorkbook = new XSSFWorkbook(this.file);

            // Заполняем Map данными
            int i = 0;
            sheet = xssfWorkbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(0);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow xssfRow = sheet.getRow(j);

                String nameOfStationDeparture = null;
                String nameOfStationDestination = null;
                String customer = null;
                double rate = 0.00d;
                Date dateLoading = null;
                String nameCargo = null;
                String keyCargo = null;

                for (int c = 0; c < row.getLastCellNum(); c++) {
                    String headerCell = row.getCell(c).getStringCellValue().trim();
                    XSSFCell cell = xssfRow.getCell(c);

                    if (headerCell.equals(JavaHelperBase.RATE_NAME_STATION_DEPARTURE)) {
                        nameOfStationDeparture = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.RATE_NAME_STATION_DESTINATION)) {
                        nameOfStationDestination = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.RATE_CUSTOMER)) {
                        customer = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.RATE_NAME_CARGO)) {
                        nameCargo = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.RATE_KEY_CARGO)) {
                        if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                            Double numericCellValue = cell.getNumericCellValue();
                            keyCargo = numericCellValue.intValue() + "";
                        } else {
                            keyCargo = cell.getStringCellValue();
                        }
                    }
                    if (headerCell.equals(JavaHelperBase.RATE_RATE)) {
                        rate = cell.getNumericCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.RATE_DATE_LOADING)) {
                        dateLoading = cell.getDateCellValue();
                        dateLoading.setHours(0);
                        dateLoading.setMinutes(0);
                        dateLoading.setSeconds(0);
                        if (dateLoading == null) dateLoading = new Date();
                    }
                }
                rateClass = new RateClass(nameOfStationDeparture, nameOfStationDestination, customer, nameCargo, keyCargo, rate, dateLoading);
                if (!mapOfRates.containsValue(rateClass)) {
                    mapOfRates.put(i, rateClass);
                    i++;
                }
                rateClass = null;
            }
            logger.debug("Body rates: {}", mapOfRates);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        } catch (InvalidFormatException e) {
            e.printStackTrace();
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
