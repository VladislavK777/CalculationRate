package com.uraltranscom.calculaterate.service.export;

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

import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.springframework.stereotype.Component;

@Component
public class WriteToFileExcel extends JavaHelperBase {
/*
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WriteToFileExcel.class);

    // Успешная выгрузка
    private static boolean isOk = false;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat();

    private static File file;
    private static File fileWagons;

    private WriteToFileExcel() {
    }

    *//*
    public static void downloadFileExcel(HttpServletResponse response, List<String>... listOfFinal) {
        try {
            xssfWorkbook = new XSSFWorkbook();
            xssfWorkbook.createSheet("Распределенные рейсы");
            xssfWorkbook.createSheet("Нераспределенные рейсы");
            xssfWorkbook.createSheet("Нераспределенные вагоны");
            xssfWorkbook.createSheet("Ошибки");

            String fileName = "Report_" + dateFormat.format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, listOfFinal);

            xssfWorkbook.close();
            isOk = true;
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }*//*

    public static void downloadFileExcel(HttpServletResponse response, Map<Route, List<Integer>> map) {
        try {
            String fileName = "Report_" + dateFormat.format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, map);

            isOk = true;
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    public static void downloadWagonsFileExcel(HttpServletResponse response, Map<String, WagonFinalInfo> map) {
        try {
            String fileName = "Report_" + dateFormat.format(new Date()) + "_wagons.xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToWagonsFileExcel(response, map);

            isOk = true;
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    public static synchronized void writeToWagonsFileExcel(HttpServletResponse response, Map<String, WagonFinalInfo> map) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileWagons))) {

                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                    XSSFRow row = sheet.getRow(0);
                    for (int c = 0; c < row.getLastCellNum(); c++) {
                        if (row.getCell(c).getStringCellValue().trim().equals("Номер вагона")) {
                            XSSFRow xssfRow = sheet.getRow(j);
                            String val = xssfRow.getCell(c).getStringCellValue();
                            for (Map.Entry<String, WagonFinalInfo> _map : map.entrySet()) {
                                if (val.equals(_map.getKey())) {
                                    for (int q = 0; q < row.getLastCellNum(); q++) {
                                        StringBuilder stationList = new StringBuilder();
                                        StringBuilder distanceList = new StringBuilder();
                                        for (WagonFinalRouteInfo wagonInfo : _map.getValue().getListRouteInfo()) {
                                            stationList.append(wagonInfo.getNameOfStationDepartureOfWagon()).append("/");
                                            distanceList.append(wagonInfo.getDistanceEmpty()).append("/");
                                        }
                                        if (row.getCell(q).getStringCellValue().trim().equals("Станция погрузки запланированная")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(stationList.toString());
                                            cell.setCellStyle(cellStyle(sheet));
                                        }
                                        if (row.getCell(q).getStringCellValue().trim().equals("Клиент Следующее задание")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(distanceList.toString());
                                            cell.setCellStyle(cellStyle(sheet));
                                        }
                                    }
                                }
                            }
                        }
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

    public static synchronized void writeToFileExcel(HttpServletResponse response, Map<Route, List<Integer>> map) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file))) {

                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
                for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                    XSSFRow row = sheet.getRow(1);
                    for (int c = 1; c < row.getLastCellNum(); c++) {
                        if (row.getCell(c).getStringCellValue().trim().equals("Номер заявки")) {
                            XSSFRow xssfRow = sheet.getRow(j);
                            String val = xssfRow.getCell(c).getStringCellValue();
                            for (Map.Entry<Route, List<Integer>> _map: map.entrySet()) {
                                if (val.equals(_map.getKey().getNumberOrder())) {
                                    for (int q = 1; q < row.getLastCellNum(); q++) {
                                        if (row.getCell(q).getStringCellValue().trim().equals("Факт.вагонов")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(_map.getValue().get(1));
                                            if (_map.getValue().get(1) < _map.getKey().getCountOrders()) {
                                                cell.setCellStyle(cellStyleRed(sheet));
                                            } else {
                                                cell.setCellStyle(cellStyle(sheet));
                                            }
                                        }
                                    }
                                }
                            }
                        }
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

    private static String buildText(int dist, int countCircle) {
        if (countCircle < MAX_COUNT_DAYS) {
            return dist + " км./" + countCircle + " " + PrefixOfDays.parsePrefixOfDays(countCircle);
        } else {
            return dist + " км./" + countCircle + " " + PrefixOfDays.parsePrefixOfDays(countCircle) + "(превышение!)";
        }
    }

    private static XSSFCellStyle cellStyle(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleRed(XSSFSheet sheet) {
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(250, 128, 114)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    // Метод записи в файл
    *//*
    public static synchronized void writeToFileExcel(HttpServletResponse response, List<String>... listOfFinalArray) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                XSSFRow row;
                Cell cell;

                // Заполняем данными
                for (String list : listOfFinalArray[i]) {
                    int rowCount = sheet.getPhysicalNumberOfRows() - 1;
                    row = sheet.createRow(rowCount + 1);
                    cell = row.createCell(0);
                    cell.setCellValue(list);
                }
            }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }*//*

    public static boolean isIsOk() {
        return isOk;
    }

    public static void setIsOk(boolean isOk) {
        WriteToFileExcel.isOk = isOk;
    }

    public static void setFile(File file) {
        WriteToFileExcel.file = file;
    }

    public static File getFileWagons() {
        return fileWagons;
    }

    public static void setFileWagons(File fileWagons) {
        WriteToFileExcel.fileWagons = fileWagons;
    }*/
}
