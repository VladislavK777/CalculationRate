package com.uraltranscom.dynamicdistributionpark.service.impl;

import com.uraltranscom.dynamicdistributionpark.model.Route;
import com.uraltranscom.dynamicdistributionpark.model.Wagon;
import com.uraltranscom.dynamicdistributionpark.service.GetList;
import com.uraltranscom.dynamicdistributionpark.service.export.WriteToFileExcel;
import com.uraltranscom.dynamicdistributionpark.util.PropertyUtil;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс получения списка вагонов
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
public class GetListOfWagonsImpl implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfWagonsImpl.class);

    // Основаная мапа, куда записываем все вагоны
    private List<Wagon> listOfWagons = new ArrayList<>();

    // Переменные для работы с файлами
    private File file ;
    private FileInputStream fileInputStream;

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    @Autowired
    private WriteToFileExcel writeToFileExcel;
    @Autowired
    private PropertyUtil propertyUtil;

    private GetListOfWagonsImpl() {
    }

    // Заполняем Map вагонами
    // TODO Переписать метод, отвязать от количества строк, избавиться от формата жесткого, необходимо и XLSX и XLS
    @Override
    public void fillMap() {
        listOfWagons.clear();
        writeToFileExcel.setFileWagons(null);
        writeToFileExcel.setFileWagons(file);

        // Получаем файл формата xls
        try {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            fileInputStream = new FileInputStream(this.file);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            // Заполняем мапу данными
            sheet = xssfWorkbook.getSheetAt(0);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow row = sheet.getRow(0);

                String numberOfWagon = null;
                String keyOfStationDeparture = null;
                String nameOfStationDeparture = null;
                String roadOfStationDeparture = null;
                String keyOfStationDestination = null;
                String nameOfStationDestination = null;
                String roadOfStationDestination = null;
                String customer = null;
                int volume = 0;
                String nameCargo = null;
                String keyCargo = null;
                String status = null;
                String distance = null;

                for (int c = 0; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.numberwagon"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        numberOfWagon = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.keystationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.namestationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.roadstationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.keystationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.namestationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.roadstationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.customer"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        customer = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.volume"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volume = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.namecargo"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.keycargo"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.distance"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        String val = Double.toString(xssfRow.getCell(c).getNumericCellValue());
                        double valueDouble = xssfRow.getCell(c).getNumericCellValue();
                        if ((valueDouble - (int) valueDouble) * 1000 == 0) {
                            val = (int) valueDouble + "";
                        }
                        distance = val;
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.status"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        status = xssfRow.getCell(c).getStringCellValue();
                    }
                }
                List<Route> list = new ArrayList<>();
                list.add(new Route(
                        keyOfStationDeparture,
                        nameOfStationDeparture,
                        roadOfStationDeparture,
                        keyOfStationDestination,
                        nameOfStationDestination,
                        roadOfStationDestination,
                        distance,
                        customer,
                        volume,
                        volume,
                        nameCargo,
                        keyCargo));
                listOfWagons.add(new Wagon(numberOfWagon, list, volume, status));
            }
            logger.debug("Body wagon: {}", listOfWagons);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла дислокации, необходим формат xlsx");
        }

    }

    public List<Wagon> getListOfWagons() {
        return listOfWagons;
    }

    public void setListOfWagons(List<Wagon> listOfWagons) {
        this.listOfWagons = listOfWagons;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        fillMap();
    }
}
