package com.uraltranscom.calculaterate.service.export;

import com.uraltranscom.calculaterate.model.route.Route;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.service.export.styles.CellStyleCommon;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 25.01.2019
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
    private static DataFormatter dataFormatter = new DataFormatter(Locale.GERMAN);

    public static void downloadFileExcel(HttpServletResponse response, ArrayList<TotalModel> listTotalModel) {
        try {
            String fileName = "Rate_" + dateFormat.format(new Date()) + ".xlsx";
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
            logger.info("size list: {}", listTotalModel.size());
            long timeStart = System.currentTimeMillis();
            ClassLoader classLoader = WriteToFileExcel.class.getClassLoader();
            File fileCalc = new File(classLoader.getResource("calculation.xlsx").getFile());
            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileCalc))) {

                ZipSecureFile.setMinInflateRatio(-1.0d);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
                CellStyleCommon cellStyleCommon = new CellStyleCommon(xssfWorkbook);
                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
                /*XSSFSheet tempSheet = xssfWorkbook.cloneSheet(0, "temp");
                buildHeader(tempSheet, cellStyleCommon);*/

                // Строка старта шапки
                int rowStartHead = 0;
                int numberTable = 1;
                for (TotalModel totalModel : listTotalModel) {
                    if (totalModel != null) {

                        // Строка финиша шапки
                        int rowFinishHead = rowStartHead + 3;
                        for (int i = rowStartHead; i <= rowFinishHead; i++) {
                            XSSFRow rowHead = sheet.createRow(i);
                            Cell head0 = rowHead.createCell(0);
                            head0.setCellValue("№п/п");
                            head0.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head1 = rowHead.createCell(1);
                            head1.setCellValue("Станция отправления");
                            head1.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head2 = rowHead.createCell(2);
                            head2.setCellValue("Дорога отпр.");
                            head2.setCellStyle(cellStyleCommon.getCellStyleHead());
                            sheet.setColumnWidth(2, 2194);

                            Cell head3 = rowHead.createCell(3);
                            head3.setCellValue("Станция назначения");
                            head3.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head4 = rowHead.createCell(4);
                            head4.setCellValue("Дорога назн.");
                            head4.setCellStyle(cellStyleCommon.getCellStyleHead());
                            sheet.setColumnWidth(4, 2194);

                            Cell head5 = rowHead.createCell(5);
                            head5.setCellValue("Наименование груза");
                            head5.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head6 = rowHead.createCell(6);
                            head6.setCellValue("Расст., км");
                            head6.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head7 = rowHead.createCell(7);
                            head7.setCellValue("Время в пути, сут");
                            head7.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head8 = rowHead.createCell(8);
                            head8.setCellValue("Погр. / выгр.");
                            head8.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head9 = rowHead.createCell(9);
                            head9.setCellValue("Оборот, сут.");
                            head9.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head10 = rowHead.createCell(10);
                            head10.setCellValue("ВО");
                            head10.setCellStyle(cellStyleCommon.getCellStyleHead());

                            Cell head11 = rowHead.createCell(11);
                            if (i == rowFinishHead) {
                                head11.setCellValue("руб/ваг.");
                            } else {
                                head11.setCellValue("ДОХОД");
                            }
                            head11.setCellStyle(cellStyleCommon.getCellStyleHeadBottom());

                            Cell head12 = rowHead.createCell(12);
                            if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                                head12.setCellValue("Тариф в собств. вагонах");
                            } else if (i == rowFinishHead) {
                                head12.setCellValue("руб/ваг.");
                            } else {
                                head12.setCellValue("РАСХОД");
                            }
                            head12.setCellStyle(cellStyleCommon.getCellStyleHeadBottom());

                            Cell head13 = rowHead.createCell(13);
                            if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                                head13.setCellValue("За нахождение в пути");
                            } else if (i == rowFinishHead) {
                                head13.setCellValue("руб/ваг.");
                            } else {
                                head13.setCellValue("ПРИБЫЛЬ");
                            }
                            head13.setCellStyle(cellStyleCommon.getCellStyleHeadBottom());

                            Cell head14 = rowHead.createCell(14);
                            if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                                head14.setCellValue("В сутки");
                            } else if (i == rowFinishHead) {
                                head14.setCellValue("руб/ваг/сут.");
                            } else {
                                head14.setCellValue("ПРИБЫЛЬ");
                            }
                            head14.setCellStyle(cellStyleCommon.getCellStyleHeadRight());
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
                        boolean isMarker = false;

                        for (Route route : totalModel.getTotalList()) {

                            int num = rowFirstRoute + 1;
                            XSSFRow row = sheet.createRow(rowFirstRoute);
                            Cell number = row.createCell(0);
                            if (!isMarker) {
                                number.setCellValue(numberTable);
                                isMarker = true;
                            } else {
                                number.setCellValue("");
                            }
                            number.setCellStyle(cellStyleCommon.getCellStyleField());

                            Cell stationDeparture = row.createCell(1);
                            stationDeparture.setCellValue(route.getStationDeparture().getNameStation());
                            if (route.isFlagNeedCalc()) {
                                stationDeparture.setCellStyle(cellStyleCommon.getCellStyleFieldNeedCalc()); // with false
                            } else {
                                stationDeparture.setCellStyle(cellStyleCommon.getCellStyleField());
                            }

                            Cell roadDeparture = row.createCell(2);
                            roadDeparture.setCellValue(route.getStationDeparture().getRoad().getNameRoad());
                            if (route.isFlagNeedCalc()) {
                                roadDeparture.setCellStyle(cellStyleCommon.getCellStyleFieldNeedCalc()); // with false
                            } else {
                                roadDeparture.setCellStyle(cellStyleCommon.getCellStyleField());
                            }

                            Cell stationDestination = row.createCell(3);
                            stationDestination.setCellValue(route.getStationDestination().getNameStation());
                            if (route.isFlagNeedCalc()) {
                                stationDestination.setCellStyle(cellStyleCommon.getCellStyleFieldNeedCalc()); // with false
                            } else {
                                stationDestination.setCellStyle(cellStyleCommon.getCellStyleField());
                            }

                            Cell roadDestination = row.createCell(4);
                            roadDestination.setCellValue(route.getStationDestination().getRoad().getNameRoad());
                            if (route.isFlagNeedCalc()) {
                                roadDestination.setCellStyle(cellStyleCommon.getCellStyleFieldNeedCalc()); // with false
                            } else {
                                roadDestination.setCellStyle(cellStyleCommon.getCellStyleField());
                            }

                            Cell cargo = row.createCell(5);
                            if (route.getRate() != 0) {
                                cargo.setCellValue(route.getCargo().getNameCargo());
                                cargo.setCellStyle(cellStyleCommon.getCellStyleFieldCargo());
                            } else {
                                cargo.setCellValue("Порожняк");
                                cargo.setCellStyle(cellStyleCommon.getCellStyleFieldCargo());
                            }

                            Cell distance = row.createCell(6);
                            distance.setCellValue(route.getDistance());
                            distance.setCellStyle(cellStyleCommon.getCellStyleFieldFormatDistance()); //with true

                            Cell countDays = row.createCell(7);
                            countDays.setCellValue(route.getCountDays());
                            countDays.setCellStyle(cellStyleCommon.getCellStyleField());

                            Cell daysLoadUnload = row.createCell(8);
                            daysLoadUnload.setCellValue(route.getCountDaysLoadAndUnload());
                            daysLoadUnload.setCellStyle(cellStyleCommon.getCellStyleField());

                            Cell fullCountDays = row.createCell(9);
                            fullCountDays.setCellFormula("SUM(H" + num + ":I" + num + ")");
                            fullCountDays.setCellStyle(cellStyleCommon.getCellStyleField());

                            Cell call9 = row.createCell(10);
                            call9.setCellValue("поваг");
                            call9.setCellStyle(cellStyleCommon.getCellStyleField());
                            sheet.autoSizeColumn(10);

                            Cell rate = row.createCell(11);
                            if (route.getRate() == 0) {
                                rate.setCellStyle(cellStyleCommon.getCellStyleFieldNull());
                            } else {
                                rate.setCellValue(route.getRate());
                                if (route.isFlagNeedCalc()) {
                                    rate.setCellStyle(cellStyleCommon.getCellStyleFieldNeedCalc()); // with false
                                } else {
                                    rate.setCellStyle(cellStyleCommon.getCellStyleFieldFormat()); // with false
                                }
                            }

                            Cell tariff = row.createCell(12);
                            if (route.getTariff() == 0) {
                                tariff.setCellStyle(cellStyleCommon.getCellStyleFieldNull());
                            } else {
                                tariff.setCellValue(route.getTariff());
                                tariff.setCellStyle(cellStyleCommon.getCellStyleFieldFormat()); // with false
                            }

                            Cell rateTariff = row.createCell(13);
                            rateTariff.setCellFormula("L" + num + "-M" + num);
                            rateTariff.setCellStyle(cellStyleCommon.getCellStyleFieldFormat()); // with false
                            sheet.setColumnWidth(13, 3182);

                            Cell cell13 = row.createCell(14);
                            cell13.setCellValue("");
                            cell13.setCellStyle(cellStyleCommon.getCellStyleFieldRightBold());

                            rowFirstRoute++;
                        }

                        // Номер последней ячейки данных
                        int lastNumberCell = rowFirstRoute;
                        int totalYieldNum = rowFirstRoute + 1;
                        XSSFRow row = sheet.createRow(rowFirstRoute);

                        Cell cell0 = row.createCell(0);
                        cell0.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell1 = row.createCell(1);
                        cell1.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell2 = row.createCell(2);
                        cell2.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell3 = row.createCell(3);
                        cell3.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell4 = row.createCell(4);
                        cell4.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell5 = row.createCell(5);
                        cell5.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        sheet.addMergedRegion(new CellRangeAddress(rowFirstRoute, rowFirstRoute, 0, 5));

                        // Строка итоговых расчетов
                        Cell totalDistance = row.createCell(6);
                        totalDistance.setCellFormula("SUM(G" + firstNumberCell + ":G" + lastNumberCell + ")");
                        totalDistance.setCellStyle(cellStyleCommon.getCellStyleFieldTotalFormatDistance()); // with true

                        Cell totalCountDays = row.createCell(7);
                        totalCountDays.setCellFormula("SUM(H" + firstNumberCell + ":H" + lastNumberCell + ")");
                        totalCountDays.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell totalCountLoadUnloadDays = row.createCell(8);
                        totalCountLoadUnloadDays.setCellFormula("SUM(I" + firstNumberCell + ":I" + lastNumberCell + ")");
                        totalCountLoadUnloadDays.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell totalFullCountDays = row.createCell(9);
                        totalFullCountDays.setCellFormula("SUM(J" + firstNumberCell + ":J" + lastNumberCell + ")");
                        totalFullCountDays.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell10 = row.createCell(10);
                        cell10.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell11 = row.createCell(11);
                        cell11.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell cell12 = row.createCell(12);
                        cell12.setCellStyle(cellStyleCommon.getCellStyleFieldTotal());

                        Cell totalRateTariff = row.createCell(13);
                        totalRateTariff.setCellFormula("SUM(N" + firstNumberCell + ":N" + lastNumberCell + ")");
                        totalRateTariff.setCellStyle(cellStyleCommon.getCellStyleFieldTotalFormat()); // with false

                        Cell yield = row.createCell(14);
                        yield.setCellFormula("N" + totalYieldNum + "/J" + totalYieldNum);
                        yield.setCellStyle(cellStyleCommon.getCellStyleFieldTotalRight()); // with false
                        sheet.autoSizeColumn(14);

                        Cell cell15 = row.createCell(15);
                        cell15.setCellValue(totalModel.getYield());

                        Cell cell16 = row.createCell(16);
                        cell16.setCellFormula("P" + totalYieldNum + "-O" + totalYieldNum);

                        Cell cell17 = row.createCell(17);
                        cell17.setCellFormula("Q" + totalYieldNum + "*J" + totalYieldNum);

                        rowStartHead = lastNumberCell + 1;
                        numberTable++;
                    }
                }
                xssfWorkbook.write(outputStream);
                outputStream.flush();
                outputStream.close();

                long timeEnd = (System.currentTimeMillis() - timeStart);
                logger.info("Work time: {} ms", timeEnd);
            }
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    /*private static void buildHeader(XSSFSheet sheet, CellStyleCommon cellStyleCommon) {
        for (int i = 0; i <= 3; i++) {
            XSSFRow rowHead = sheet.createRow(i);
            Cell head0 = rowHead.createCell(0);
            head0.setCellValue("№п/п");
            head0.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head1 = rowHead.createCell(1);
            head1.setCellValue("Станция отправления");
            head1.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head2 = rowHead.createCell(2);
            head2.setCellValue("Дорога отпр.");
            head2.setCellStyle(cellStyleCommon.getCellStyleHead());
            sheet.setColumnWidth(2, 2194);

            Cell head3 = rowHead.createCell(3);
            head3.setCellValue("Станция назначения");
            head3.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head4 = rowHead.createCell(4);
            head4.setCellValue("Дорога назн.");
            head4.setCellStyle(cellStyleCommon.getCellStyleHead());
            sheet.setColumnWidth(4, 2194);

            Cell head5 = rowHead.createCell(5);
            head5.setCellValue("Наименование груза");
            head5.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head6 = rowHead.createCell(6);
            head6.setCellValue("Расст., км");
            head6.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head7 = rowHead.createCell(7);
            head7.setCellValue("Время в пути, сут");
            head7.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head8 = rowHead.createCell(8);
            head8.setCellValue("Погр. / выгр.");
            head8.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head9 = rowHead.createCell(9);
            head9.setCellValue("Оборот, сут.");
            head9.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head10 = rowHead.createCell(10);
            head10.setCellValue("ВО");
            head10.setCellStyle(cellStyleCommon.getCellStyleHead());

            Cell head11 = rowHead.createCell(11);
            if (i == 3) {
                head11.setCellValue("руб/ваг.");
            } else {
                head11.setCellValue("ДОХОД");
            }
            head11.setCellStyle(cellStyleCommon.getCellStyleHeadBottom());

            Cell head12 = rowHead.createCell(12);
            if (i == 2 || i == 1) {
                head12.setCellValue("Тариф в собств. вагонах");
            } else if (i == 3) {
                head12.setCellValue("руб/ваг.");
            } else {
                head12.setCellValue("РАСХОД");
            }
            head12.setCellStyle(cellStyleCommon.getCellStyleHeadBottom());

            Cell head13 = rowHead.createCell(13);
            if (i == 2 || i == 1) {
                head13.setCellValue("За нахождение в пути");
            } else if (i == 3) {
                head13.setCellValue("руб/ваг.");
            } else {
                head13.setCellValue("ПРИБЫЛЬ");
            }
            head13.setCellStyle(cellStyleCommon.getCellStyleHeadBottom());

            Cell head14 = rowHead.createCell(14);
            if (i == 2 || i == 1) {
                head14.setCellValue("В сутки");
            } else if (i == 3) {
                head14.setCellValue("руб/ваг/сут.");
            } else {
                head14.setCellValue("ПРИБЫЛЬ");
            }
            head14.setCellStyle(cellStyleCommon.getCellStyleHeadRight());
        }

        for (int i = 0; i < 11; i++) {
            sheet.addMergedRegion(new CellRangeAddress(0, 4, i, i));
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 3, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
        for (int i = 12; i < 15; i++) {
            sheet.addMergedRegion(new CellRangeAddress(1, 3, i, i));
        }
    }

    private static void copyRow(XSSFSheet srcSheet, XSSFSheet destSheet, XSSFRow srcRow, XSSFRow destRow, Map<Integer, XSSFCellStyle> styleMap) {
        Set<CellRangeAddress> mergedRegions = new TreeSet<CellRangeAddress>();
        destRow.setHeight(srcRow.getHeight());
        for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
            XSSFCell oldCell = srcRow.getCell(j);
            XSSFCell newCell = destRow.getCell(j);
            if (oldCell != null) {
                if (newCell == null) {
                    newCell = destRow.createCell(j);
                }
                copyCell(oldCell, newCell, styleMap);
                CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());
                if (mergedRegion != null) {
                    CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow(), mergedRegion.getFirstColumn(), mergedRegion.getLastRow(), mergedRegion.getLastColumn());
                    if (isNewMergedRegion(newMergedRegion, mergedRegions)) {
                        mergedRegions.add(newMergedRegion);
                        destSheet.addMergedRegion(newMergedRegion);
                    }
                }
            }
        }

    }

    private static void copyCell(XSSFCell oldCell, XSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap) {
        if(styleMap != null) {
            if(oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()){
                newCell.setCellStyle(oldCell.getCellStyle());
            } else{
                int stHashCode = oldCell.getCellStyle().hashCode();
                XSSFCellStyle newCellStyle = styleMap.get(stHashCode);
                if(newCellStyle == null){
                    newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
                    newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
                    styleMap.put(stHashCode, newCellStyle);
                }
                newCell.setCellStyle(newCellStyle);
            }
        }
        switch(oldCell.getCellType()) {
            case STRING:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case BLANK:
                newCell.setCellType(CellType.BLANK);
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            default:
                break;
        }

    }

    private static CellRangeAddress getMergedRegion(XSSFSheet sheet, int rowNum, short cellNum) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress merged = sheet.getMergedRegion(i);
            if (merged.isInRange(rowNum, cellNum)) {
                return merged;
            }
        }
        return null;
    }

    private static boolean isNewMergedRegion(CellRangeAddress newMergedRegion, Collection<CellRangeAddress> mergedRegions) {
        return !mergedRegions.contains(newMergedRegion);
    }*/

    /*private static XSSFFont getFont(XSSFSheet sheet) {
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontName("Times New Roman");
        font.setFontHeight(12.0);
        return font;
    }

    private static short format(XSSFSheet sheet, boolean with00) {
        XSSFDataFormat dataFormat = sheet.getWorkbook().createDataFormat();
        return with00 ? dataFormat.getFormat("#,##0") : dataFormat.getFormat("#,##0.00");
    }*/

    /*private static XSSFCellStyle cellStyleHead(XSSFCellStyle cellStyle) {
        //cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleHeadBottom(XSSFCellStyle cellStyle) {
        //cellStyle.setFont(getFont(sheet));
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

    private static XSSFCellStyle cellStyleHeadRight(XSSFCellStyle cellStyle) {
       // XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
       // cellStyle.setFont(getFont(sheet));
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

    private static XSSFCellStyle cellStyleField(XSSFCellStyle cellStyle) {
       // XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldFormat(XSSFCellStyle cellStyle, boolean with00) {
        //XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        //cellStyle.setDataFormat(format(sheet, with00));
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldNull(XSSFCellStyle cellStyle) {
        //XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //XSSFFont font = getFont(sheet);
        //font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        //cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldRightBold(XSSFCellStyle cellStyle) {
       // XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldCargo(XSSFCellStyle cellStyle) {
        //XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldNeedCalc(XSSFCellStyle cellStyle, boolean with00) {
        //XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //XSSFFont font = getFont(sheet);
        //font.setBold(true);
        //cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(214, 214, 214)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //cellStyle.setDataFormat(format(sheet, with00));
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldTotal(XSSFCellStyle cellStyle) {
        //XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //cellStyle.setFont(getFont(sheet));
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

    private static XSSFCellStyle cellStyleFieldTotalFormat(XSSFCellStyle cellStyle, boolean with00) {
        //XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
       // cellStyle.setDataFormat(format(sheet, with00));
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleFieldTotalRight(XSSFCellStyle cellStyle, boolean with00) {
        //XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        //cellStyle.setFont(getFont(sheet));
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        //cellStyle.setDataFormat(format(sheet, with00));
        return cellStyle;
    }*/
}
