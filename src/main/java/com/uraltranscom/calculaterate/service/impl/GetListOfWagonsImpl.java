package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.service.GetList;
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

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    @Autowired
    private WriteToFileExcel writeToFileExcel;

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
            xssfWorkbook = new XSSFWorkbook(this.file);

            // Заполняем мапу данными
            sheet = xssfWorkbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(0);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow xssfRow = sheet.getRow(j);

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
                    String headerCell = row.getCell(c).getStringCellValue().trim();
                    XSSFCell cell = xssfRow.getCell(c);

                    if (headerCell.equals(JavaHelperBase.WAGON_NUMBER_WAGON)) {
                        numberOfWagon = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_KEY_STATION_DEPARTURE)) {
                        keyOfStationDeparture = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_NAME_STATION_DEPARTURE)) {
                        nameOfStationDeparture = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_ROAD_STATION_DEPARTURE)) {
                        roadOfStationDeparture = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_KEY_STATION_DESTINATION)) {
                        keyOfStationDestination = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_NAME_STATION_DESTINATION)) {
                        nameOfStationDestination = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_ROAD_STATION_DESTINATION)) {
                        roadOfStationDestination = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_CUSTOMER)) {
                        customer = cell.getStringCellValue();
                    }
                    if (headerCell.trim().equals(JavaHelperBase.WAGON_VOLUME)) {
                        volume = (int) cell.getNumericCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_NAME_CARGO)) {
                        nameCargo = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_KEY_CARGO)) {
                        keyCargo = cell.getStringCellValue();
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_DISTANCE)) {
                        String val = Double.toString(cell.getNumericCellValue());
                        double valueDouble = cell.getNumericCellValue();
                        if ((valueDouble - (int) valueDouble) * 1000 == 0) {
                            val = (int) valueDouble + "";
                        }
                        distance = val;
                    }
                    if (headerCell.equals(JavaHelperBase.WAGON_STATUS)) {
                        status = cell.getStringCellValue();
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
        } catch (InvalidFormatException e) {
            e.printStackTrace();
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
