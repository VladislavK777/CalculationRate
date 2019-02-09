package com.uraltranscom.calculaterate.service.export;

import com.uraltranscom.calculaterate.model.Route;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Класс записи в файл Excel
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 25.01.2019
 *
 * 25.01.2019
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

    public static void downloadFileExcel(HttpServletResponse response, ArrayList<TotalModel> listTotalModel) {
        try {
            String fileName = "Rate_" + dateFormat.format(new Date()) + "_" + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, listTotalModel);

            isOk = true;
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    private static synchronized void writeToFileExcel(HttpServletResponse response, ArrayList<TotalModel> listTotalModel) {
        try {
            ClassLoader classLoader = WriteToFileExcel.class.getClassLoader();
            File fileCalc = new File(classLoader.getResource("calculation.xlsx").getFile());
            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileCalc))) {

                ZipSecureFile.setMinInflateRatio(-1.0d);
                xssfWorkbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

                // Строка старта шапки
                int rowStartHead = 0;
                int numberTable = 1;
                for (TotalModel totalModel: listTotalModel) {
                    if (totalModel != null) {

                        // Строка финиша шапки
                        int rowFinishHead = rowStartHead + 3;

                        for (int i = rowStartHead; i <= rowFinishHead; i++) {
                            XSSFRow rowHead = sheet.createRow(i);
                            Cell head0 = rowHead.createCell(0);
                            head0.setCellValue("№п/п");
                            head0.setCellStyle(cellStyleHead(sheet));

                            Cell head1 = rowHead.createCell(1);
                            head1.setCellValue("Станция отправления");
                            head1.setCellStyle(cellStyleHead(sheet));

                            Cell head2 = rowHead.createCell(2);
                            head2.setCellValue("Дорога отправления");
                            head2.setCellStyle(cellStyleHead(sheet));

                            Cell head3 = rowHead.createCell(3);
                            head3.setCellValue("Станция назначения");
                            head3.setCellStyle(cellStyleHead(sheet));

                            Cell head4 = rowHead.createCell(4);
                            head4.setCellValue("Дорога назначения");
                            head4.setCellStyle(cellStyleHead(sheet));

                            Cell head5 = rowHead.createCell(5);
                            head5.setCellValue("Наименование груза");
                            head5.setCellStyle(cellStyleHead(sheet));

                            Cell head6 = rowHead.createCell(6);
                            head6.setCellValue("Расст., км");
                            head6.setCellStyle(cellStyleHead(sheet));

                            Cell head7 = rowHead.createCell(7);
                            head7.setCellValue("Время в пути, сут");
                            head7.setCellStyle(cellStyleHead(sheet));

                            Cell head8 = rowHead.createCell(8);
                            head8.setCellValue("Погр. / выгр.");
                            head8.setCellStyle(cellStyleHead(sheet));

                            Cell head9 = rowHead.createCell(9);
                            head9.setCellValue("Оборот, сут.");
                            head9.setCellStyle(cellStyleHead(sheet));

                            Cell head10 = rowHead.createCell(10);
                            head10.setCellValue("ВО");
                            head10.setCellStyle(cellStyleHead(sheet));

                            Cell head11 = rowHead.createCell(11);
                            if (i == rowFinishHead) {
                                head11.setCellValue("руб/ваг.");
                            } else {
                                head11.setCellValue("ДОХОД");
                            }
                            head11.setCellStyle(cellStyleHeadBottom(sheet));

                            Cell head12 = rowHead.createCell(12);
                            if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                                head12.setCellValue("Тариф в собств. вагонах");
                            } else if (i == rowFinishHead) {
                                head12.setCellValue("руб/ваг.");
                            } else {
                                head12.setCellValue("РАСХОД");
                            }
                            head12.setCellStyle(cellStyleHeadBottom(sheet));

                            Cell head13 = rowHead.createCell(13);
                            if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                                head13.setCellValue("За нахождение в пути");
                            } else if (i == rowFinishHead) {
                                head13.setCellValue("руб/ваг.");
                            } else {
                                head13.setCellValue("ПРИБЫЛЬ");
                            }
                            head13.setCellStyle(cellStyleHeadBottom(sheet));

                            Cell head14 = rowHead.createCell(14);
                            if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                                head14.setCellValue("В сутки");
                            } else if (i == rowFinishHead) {
                                head14.setCellValue("руб/ваг/сут.");
                            } else {
                                head14.setCellValue("ПРИБЫЛЬ");
                            }
                            head14.setCellStyle(cellStyleHeadRight(sheet));
                        }

                        for (int i = 0; i < 11; i++) {
                            sheet.addMergedRegion(new CellRangeAddress(rowStartHead, rowFinishHead, i, i));
                        }
                        sheet.addMergedRegion(new CellRangeAddress(rowStartHead, rowFinishHead - 1, 11, 11));
                        sheet.addMergedRegion(new CellRangeAddress(rowStartHead, rowStartHead, 13, 14));
                        for (int i = 12; i < 15; i++) {
                            sheet.addMergedRegion(new CellRangeAddress(rowStartHead + 1, rowFinishHead - 1, i, i));
                        }

                        // Строка первого рейса
                        int rowFirstRoute = rowFinishHead + 1;
                        // Номер первой ячейки данных
                        int firstNumberCell = rowFirstRoute + 1;

                        for (Route route : totalModel.getTotalList()) {
                            int num = rowFirstRoute + 1;
                            XSSFRow row = sheet.createRow(rowFirstRoute);
                            Cell number = row.createCell(0);
                            number.setCellValue(numberTable);
                            number.setCellStyle(cellStyleField(sheet));

                            Cell stationDeparture = row.createCell(1);
                            stationDeparture.setCellValue(route.getStationDeparture().getNameStation());
                            stationDeparture.setCellStyle(cellStyleField(sheet));

                            Cell roadDeparture = row.createCell(2);
                            roadDeparture.setCellValue(route.getStationDeparture().getRoad().getNameRoad());
                            roadDeparture.setCellStyle(cellStyleField(sheet));

                            Cell stationDestination = row.createCell(3);
                            stationDestination.setCellValue(route.getStationDestination().getNameStation());
                            stationDestination.setCellStyle(cellStyleField(sheet));

                            Cell roadDestination = row.createCell(4);
                            roadDestination.setCellValue(route.getStationDestination().getRoad().getNameRoad());
                            roadDestination.setCellStyle(cellStyleField(sheet));

                            Cell cargo = row.createCell(5);
                            if (route.getRate() != 0) {
                                cargo.setCellValue(route.getCargo().getNameCargo());
                                cargo.setCellStyle(cellStyleFieldCargo(sheet));
                            } else {
                                cargo.setCellValue("Порожняк");
                                cargo.setCellStyle(cellStyleFieldCargo(sheet));
                            }

                            Cell distance = row.createCell(6);
                            distance.setCellValue(route.getDistance());
                            distance.setCellStyle(cellStyleField(sheet));

                            Cell countDays = row.createCell(7);
                            countDays.setCellValue(route.getCountDays());
                            countDays.setCellStyle(cellStyleField(sheet));

                            Cell daysLoadUnload = row.createCell(8);
                            daysLoadUnload.setCellValue(route.getCountDaysLoadAndUnload());
                            daysLoadUnload.setCellStyle(cellStyleField(sheet));

                            Cell fullCountDays = row.createCell(9);
                            fullCountDays.setCellFormula("SUM(H" + num + ":I" + num + ")");
                            fullCountDays.setCellStyle(cellStyleField(sheet));

                            Cell call9 = row.createCell(10);
                            call9.setCellValue("поваг");
                            call9.setCellStyle(cellStyleField(sheet));
                            sheet.autoSizeColumn(10);

                            Cell rate = row.createCell(11);
                            rate.setCellValue(route.getRate());
                            if (route.isFlagNeedCalc()) {
                                rate.setCellStyle(cellStyleFieldNeedCalc(sheet));
                            } else {
                                rate.setCellStyle(cellStyleField(sheet));
                            }

                            Cell tariff = row.createCell(12);
                            tariff.setCellValue(route.getTariff());
                            tariff.setCellStyle(cellStyleField(sheet));

                            Cell rateTariff = row.createCell(13);
                            rateTariff.setCellFormula("L" + num + "-M" + num);
                            rateTariff.setCellStyle(cellStyleField(sheet));
                            sheet.autoSizeColumn(13);

                            Cell cell13 = row.createCell(14);
                            cell13.setCellValue("");
                            cell13.setCellStyle(cellStyleFieldRightBold(sheet));

                            rowFirstRoute++;
                        }

                        // Номер последней ячейки данных
                        int lastNumberCell = rowFirstRoute;
                        int totalYieldNum = rowFirstRoute + 1;
                        XSSFRow row = sheet.createRow(rowFirstRoute);

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

                        Cell cell5 = row.createCell(5);
                        cell4.setCellStyle(cellStyleFieldTotal(sheet));

                        sheet.addMergedRegion(new CellRangeAddress(rowFirstRoute, rowFirstRoute, 0, 5));
                        // Строка итоговых расчетов
                        Cell totalDistance = row.createCell(6);
                        totalDistance.setCellFormula("SUM(G" + firstNumberCell + ":G" + lastNumberCell + ")");
                        totalDistance.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell totalCountDays = row.createCell(7);
                        totalCountDays.setCellFormula("SUM(H" + firstNumberCell + ":H" + lastNumberCell + ")");
                        totalCountDays.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell totalCountLoadUnloadDays = row.createCell(8);
                        totalCountLoadUnloadDays.setCellFormula("SUM(I" + firstNumberCell + ":I" + lastNumberCell + ")");
                        totalCountLoadUnloadDays.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell totalFullCountDays = row.createCell(9);
                        totalFullCountDays.setCellFormula("SUM(J" + firstNumberCell + ":J" + lastNumberCell + ")");
                        totalFullCountDays.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell cell10 = row.createCell(10);
                        cell10.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell cell11 = row.createCell(11);
                        cell11.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell cell12 = row.createCell(12);
                        cell12.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell totalRateTariff = row.createCell(13);
                        totalRateTariff.setCellFormula("SUM(N" + firstNumberCell + ":N" + lastNumberCell + ")");
                        totalRateTariff.setCellStyle(cellStyleFieldTotal(sheet));

                        Cell yield = row.createCell(14);
                        yield.setCellFormula("N" + totalYieldNum + "/J" + totalYieldNum);
                        yield.setCellStyle(cellStyleFieldTotalRight(sheet));
                        sheet.autoSizeColumn(14);

                        rowStartHead = lastNumberCell + 1;
                        numberTable++;
                    }
                }

                xssfWorkbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    private static XSSFFont getFont(XSSFSheet sheet) {
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontName("Times New Roman");
        font.setFontHeight(12.0);
        return font;
    }

    private static XSSFCellStyle cellStyleHead(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleHeadBottom(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleHeadRight(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleField(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldRightBold(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldCargo(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldNeedCalc(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont font = getFont(sheet);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 160, 122)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldTotal(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldTotalRight(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        return cellStyle;
    }
}
