package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.model.RatesList;
import com.uraltranscom.calculaterate.model.station.Road;
import com.uraltranscom.calculaterate.model.station.Station;
import com.uraltranscom.calculaterate.util.GetVolumeGroup;
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
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Получени списка ставок из файла
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 29.01.2019
 *
 * 29.01.2019
 *   1. Версия 1.0
 *
 */

@Component
@NoArgsConstructor
public class GetListRates {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListRates.class);

    private Set<RatesList> listRates = new HashSet();

    public Set<RatesList> getListRates(File file) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(3);
            for (int i = 6; i < sheet.getLastRowNum() + 1; i++) {
                XSSFRow xssfRow = sheet.getRow(i);
                int volume = 0;
                String nameStationFrom = null;
                String roadStationFrom = null;
                String nameStationTo = null;
                String roadStationTo = null;
                double actualRate = 0.00;
                for (int j = 5; j < 13; j++) {
                    String headerCell = row.getCell(j).getStringCellValue().trim();
                    XSSFCell cell = xssfRow.getCell(j);

                    if (headerCell.equals("Осн. объем")) {
                        volume = (int) cell.getNumericCellValue();
                    }
                    if (headerCell.equals("Ст.отпр")) {
                        nameStationFrom = cell.getStringCellValue();
                    }
                    if (headerCell.equals("Дор. отпрв.")) {
                        roadStationFrom = cell.getStringCellValue();
                        if (roadStationFrom.equals("УЗБ")) roadStationFrom = "УТИ";
                    }
                    if (headerCell.equals("Назначение")) {
                        nameStationTo = cell.getStringCellValue();
                    }
                    if (headerCell.equals("Дор. назн.")) {
                        roadStationTo = cell.getStringCellValue();
                        if (roadStationTo.equals("УЗБ")) roadStationTo = "УТИ";
                    }
                    if (headerCell.equals("Актуальная ставка")) {
                        actualRate = cell.getNumericCellValue();
                    }
                }
                RatesList ratesList = new RatesList(GetVolumeGroup.getVolumeGroup(volume), new Station(null, nameStationFrom, new Road(0, roadStationFrom)), new Station(null, nameStationTo, new Road(0, roadStationTo)), actualRate);
                listRates.add(ratesList);
            }
            logger.debug("Body rates: {}", listRates);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        } catch (InvalidFormatException e) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
        return listRates;
    }
}
