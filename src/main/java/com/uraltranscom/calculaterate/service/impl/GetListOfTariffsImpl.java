package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.TariffClass;
import com.uraltranscom.calculaterate.service.GetList;
import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Класс получения списка порожних рейсов
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
public class GetListOfTariffsImpl implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfTariffsImpl.class);

    // Основаная мапа, куда записываем все маршруты
    private Map<Integer, TariffClass> mapOfEmptyRoutes = new HashMap<>();

    // Переменные для работы с файлами
    private File file;
    private FileInputStream fileInputStream;

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    private GetListOfTariffsImpl() {
    }

    // Заполняем Map вагонами
    @Override
    public void fillMap() {
        mapOfEmptyRoutes.clear();
        // Получаем файл формата xls
        try {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            fileInputStream = new FileInputStream(this.file);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            // Заполняем Map данными
            sheet = xssfWorkbook.getSheetAt(0);
            int i = 0;
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow row = sheet.getRow(0);

                String nameOfStationDeparture = null;
                String nameOfStationDestination = null;
                String nameCargo = null;
                double tariff = 0.00d;

                for (int c = 0; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.TARIFF_NAME_STATION_DEPARTURE)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.TARIFF_NAME_STATION_DESTINATION)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.TARIFF_LAST_CARGO)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(JavaHelperBase.TARIFF_TARIFF)) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        tariff = xssfRow.getCell(c).getNumericCellValue();
                    }
                }
                if (!mapOfEmptyRoutes.containsValue(new TariffClass(nameOfStationDeparture, nameOfStationDestination, nameCargo, tariff))) {
                    mapOfEmptyRoutes.put(i, new TariffClass(nameOfStationDeparture, nameOfStationDestination, nameCargo, tariff));
                    i++;
                }
            }
            logger.debug("Body emptyRoutes: {}", mapOfEmptyRoutes);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
    }

    public Map<Integer, TariffClass> getMapOfEmptyRoutes() {
        return mapOfEmptyRoutes;
    }

    public void setMapOfEmptyRoutes(Map<Integer, TariffClass> mapOfEmptyRoutes) {
        this.mapOfEmptyRoutes = mapOfEmptyRoutes;
    }

    public void setFile(File file) {
        this.file = file;
        fillMap();
    }
}
