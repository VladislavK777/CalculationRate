package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.GroupListBasicDate;
import com.uraltranscom.calculaterate.model.route.Cargo;
import com.uraltranscom.calculaterate.model.station.Station;
import lombok.NoArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 06.03.2019
 */

@Component
@NoArgsConstructor
public class GetGroupListBasicDate {
    private static Logger logger = LoggerFactory.getLogger(GetGroupListBasicDate.class);
    private List<GroupListBasicDate> groupListBasicDates = new ArrayList<>();

    public List<GroupListBasicDate> getGroupListBasicDateFromFile(File file) {
        groupListBasicDates.clear();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFRow row = xssfSheet.getRow(0);
            for (int i = 1; i < xssfSheet.getLastRowNum() + 1; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i);

                int number = 0;
                String stationFromId = null;
                String stationFromName = null;
                String stationToId = null;
                String stationToName = null;
                String cargoId = null;
                String cargoName = null;
                int volume = 0;

                for (int j = 0; j < 7; j++) {
                    String cellHeader = row.getCell(j).getStringCellValue().trim();
                    XSSFCell cell = xssfRow.getCell(j);
                    if (cellHeader.equals("Станция отправления")) {
                        if (cell == null) {
                            stationFromName = null;
                        } else {
                            stationFromName = cell.getStringCellValue();
                        }
                    }
                    if (cellHeader.equals("Код ст. отпр.")) {
                        if (cell == null) {
                            stationFromId = null;
                        } else {
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    stationFromId = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    stationFromId = cell.getStringCellValue();
                                    break;
                            }
                        }
                    }
                    if (cellHeader.equals("Станция назначения")) {
                        if (cell == null) {
                            stationToName = null;
                        } else {
                            stationToName = cell.getStringCellValue();
                        }
                    }
                    if (cellHeader.equals("Код ст. назн.")) {
                        if (cell == null) {
                            stationToId = null;
                        } else {
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    stationToId = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    stationToId = cell.getStringCellValue();
                                    break;
                            }
                        }
                    }
                    if (cellHeader.equals("Груз")) {
                        if (cell == null) {
                            cargoName = null;
                        } else {
                            cargoName = cell.getStringCellValue();
                        }
                    }
                    if (cellHeader.equals("Код груза")) {
                        if (cell == null) {
                            cargoId = null;
                        } else {
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    cargoId = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    cargoId = cell.getStringCellValue();
                                    break;
                            }
                        }
                    }
                    if (cellHeader.equals("Объем")) {
                        volume = (int) cell.getNumericCellValue();
                    }
                    number = i + 1;
                }

                GroupListBasicDate groupListBasicDate = new GroupListBasicDate(
                        number,
                        new Station(stationFromId, stationFromName,null),
                        new Station(stationToId, stationToName, null),
                        new Cargo(cargoId, cargoName),
                        volume);

                groupListBasicDates.add(groupListBasicDate);
                logger.debug("Body getGroupListBasicDate: {}", groupListBasicDates);
            }
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException | InvalidFormatException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
        return groupListBasicDates;
    }
}
