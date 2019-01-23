package com.uraltranscom.calculaterate.service.export;

import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Класс записи в файл Excel
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */


@Component
@NoArgsConstructor
@Data
public class WriteToFileExcel {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WriteToFileExcel.class);

    // Успешная выгрузка
    private static boolean isOk = false;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static XSSFWorkbook xssfWorkbook;
    private static File file;

    public static void downloadFileExcel(HttpServletResponse response, TotalModel totalModel) {
        try {
            String fileName = "Rate_" + dateFormat.format(new Date()) + "_" + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, totalModel);

            isOk = true;
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    private static synchronized void writeToFileExcel(HttpServletResponse response, TotalModel totalModel) {
        try {
            ClassLoader classLoader = WriteToFileExcel.class.getClassLoader();
            File fileCalc = new File(classLoader.getResource("calculation.xlsx").getFile());
            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileCalc))) {

                ZipSecureFile.setMinInflateRatio(-1.0d);
                xssfWorkbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

                // Строка текущего рейса вагона
                int rowI = 4;
                // Номер первой ячейки данных
                int firstNumberCell = rowI + 1;

                for (Route route: totalModel.getTotalList()) {
                    int num = rowI + 1;
                    XSSFRow row = sheet.createRow(rowI);
                    Cell stationDeparture = row.createCell(0);
                    stationDeparture.setCellValue(route.getStationDeparture().getNameStation());
                    stationDeparture.setCellStyle(cellStyleField(sheet));

                    Cell roadDeparture = row.createCell(1);
                    roadDeparture.setCellValue(route.getStationDeparture().getRoad().getNameRoad());
                    roadDeparture.setCellStyle(cellStyleField(sheet));

                    Cell stationDestination = row.createCell(2);
                    stationDestination.setCellValue(route.getStationDestination().getNameStation());
                    stationDestination.setCellStyle(cellStyleField(sheet));

                    Cell roadDestination = row.createCell(3);
                    roadDestination.setCellValue(route.getStationDestination().getRoad().getNameRoad());
                    roadDestination.setCellStyle(cellStyleField(sheet));

                    Cell cargo = row.createCell(4);
                    if (route.getRate() != 0) {
                        cargo.setCellValue(route.getCargo().getNameCargo());
                        cargo.setCellStyle(cellStyleField(sheet));
                    } else {
                        cargo.setCellValue("ПОРОЖНЯК");
                        cargo.setCellStyle(cellStyleField(sheet));
                    }

                    Cell distance = row.createCell(5);
                    distance.setCellValue(route.getDistance());
                    distance.setCellStyle(cellStyleField(sheet));

                    Cell countDays = row.createCell(6);
                    countDays.setCellValue(route.getCountDays());
                    countDays.setCellStyle(cellStyleField(sheet));

                    Cell daysLoadUnload = row.createCell(7);
                    daysLoadUnload.setCellValue(route.getCountDaysLoadAndUnload());
                    daysLoadUnload.setCellStyle(cellStyleField(sheet));

                    Cell fullCountDays = row.createCell(8);
                    fullCountDays.setCellFormula("SUM(G" + num + ":H" + num + ")");
                    fullCountDays.setCellStyle(cellStyleField(sheet));

                    Cell call9 = row.createCell(9);
                    call9.setCellValue("поваг");
                    call9.setCellStyle(cellStyleField(sheet));

                    Cell rate = row.createCell(10);
                    rate.setCellValue(route.getRate());
                    if (route.isFlagNeedCalc()) {
                        rate.setCellStyle(cellStyleFieldNeedCalc(sheet));
                    } else {
                        rate.setCellStyle(cellStyleField(sheet));
                    }

                    Cell tariff = row.createCell(11);
                    tariff.setCellValue(route.getTariff());
                    tariff.setCellStyle(cellStyleField(sheet));

                    Cell rateTariff = row.createCell(12);
                    if (route.getRate() != 0) {
                        rateTariff.setCellFormula("K" + num);
                        rateTariff.setCellStyle(cellStyleField(sheet));
                    } else {
                        rateTariff.setCellFormula("L" + num);
                        rateTariff.setCellStyle(cellStyleField(sheet));
                    }

                    Cell cell13 = row.createCell(13);
                    cell13.setCellValue("");
                    cell13.setCellStyle(cellStyleFieldRightBoard(sheet));

                    rowI++;
                }

                // Номер последней ячейки данных
                int lastNumberCell = rowI;
                int totalYieldNum = rowI + 1;
                XSSFRow row = sheet.createRow(rowI);

                Cell cell0 = row.createCell(0);
                cell0.setCellStyle(cellStyleFieldTotal(sheet));

                Cell cell1 = row.createCell(1);
                cell1.setCellStyle(cellStyleFieldTotal(sheet));

                Cell cell2 = row.createCell(2);
                cell2.setCellStyle(cellStyleFieldTotal(sheet));

                Cell cell3 = row.createCell(3);
                cell3.setCellStyle(cellStyleFieldTotal(sheet));

                Cell cell4 = row.createCell(4);
                cell4.setCellStyle(cellStyleFieldTotal(sheet));

                sheet.addMergedRegion(new CellRangeAddress(rowI, rowI,0, 4));
                // Строка итоговых расчетов
                Cell totalDistance = row.createCell(5);
                totalDistance.setCellFormula("SUM(F" + firstNumberCell + ":F" + lastNumberCell + ")");
                totalDistance.setCellStyle(cellStyleFieldTotal(sheet));

                Cell totalCountDays = row.createCell(6);
                totalCountDays.setCellFormula("SUM(G" + firstNumberCell + ":G" + lastNumberCell + ")");
                totalCountDays.setCellStyle(cellStyleFieldTotal(sheet));

                Cell totalCountLoadUnloadDays = row.createCell(7);
                totalCountLoadUnloadDays.setCellFormula("SUM(H" + firstNumberCell + ":H" + lastNumberCell + ")");
                totalCountLoadUnloadDays.setCellStyle(cellStyleFieldTotal(sheet));

                Cell totalFullCountDays = row.createCell(8);
                totalFullCountDays.setCellFormula("SUM(I" + firstNumberCell + ":I" + lastNumberCell + ")");
                totalFullCountDays.setCellStyle(cellStyleFieldTotal(sheet));

                Cell cell9 = row.createCell(9);
                cell9.setCellStyle(cellStyleFieldTotal(sheet));

                Cell cell10 = row.createCell(10);
                cell10.setCellStyle(cellStyleFieldTotal(sheet));

                Cell cell11 = row.createCell(11);
                cell11.setCellStyle(cellStyleFieldTotal(sheet));

                Cell totalRateTariff = row.createCell(12);
                totalRateTariff.setCellFormula("SUM(M" + firstNumberCell + ":M" + lastNumberCell + ")");
                totalRateTariff.setCellStyle(cellStyleFieldTotal(sheet));

                Cell yield = row.createCell(13);
                yield.setCellFormula("SUM(M" + totalYieldNum + "/I" + totalYieldNum + ")");
                yield.setCellStyle(cellStyleForFieldYield(sheet));

                xssfWorkbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    private static XSSFCellStyle cellStyleField(XSSFSheet sheet) {
        XSSFFont fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        (fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldRightBoard(XSSFSheet sheet) {
        XSSFFont fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        (fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldNeedCalc(XSSFSheet sheet) {
        XSSFFont fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        (fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 160, 122)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldTotal(XSSFSheet sheet) {
        XSSFFont fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        (fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleForFieldYield(XSSFSheet sheet) {
        XSSFFont fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        (fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFDataFormat dataFormat = sheet.getWorkbook().createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("0.00"));
        return cellStyle;
    }
}
