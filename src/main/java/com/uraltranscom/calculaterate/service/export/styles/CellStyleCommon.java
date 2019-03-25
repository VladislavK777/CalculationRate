package com.uraltranscom.calculaterate.service.export.styles;

import lombok.Data;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 10.03.2019
 */

@Data
public class CellStyleCommon {
    private static Logger logger = LoggerFactory.getLogger(CellStyleCommon.class);

    private XSSFCellStyle cellStyleField;
    private XSSFCellStyle cellStyleFieldFormat;
    private XSSFCellStyle cellStyleFieldFormatDistance;
    private XSSFCellStyle cellStyleHead;
    private XSSFCellStyle cellStyleHeadBottom;
    private XSSFCellStyle cellStyleHeadRight;
    private XSSFCellStyle cellStyleFieldNull;
    private XSSFCellStyle cellStyleFieldRightBold;
    private XSSFCellStyle cellStyleFieldCargo;
    private XSSFCellStyle cellStyleFieldNeedCalc;
    private XSSFCellStyle cellStyleFieldTotal;
    private XSSFCellStyle cellStyleFieldTotalFormat;
    private XSSFCellStyle cellStyleFieldTotalFormatDistance;
    private XSSFCellStyle cellStyleFieldTotalRight;
    private XSSFFont xssfFont;
    private XSSFDataFormat format;

    public CellStyleCommon(XSSFWorkbook xssfWorkbook) {
        this.cellStyleField = xssfWorkbook.createCellStyle();
        this.cellStyleFieldFormat = xssfWorkbook.createCellStyle();
        this.cellStyleFieldFormatDistance = xssfWorkbook.createCellStyle();
        this.cellStyleHead = xssfWorkbook.createCellStyle();
        this.cellStyleHeadBottom = xssfWorkbook.createCellStyle();
        this.cellStyleHeadRight = xssfWorkbook.createCellStyle();
        this.cellStyleFieldNull = xssfWorkbook.createCellStyle();
        this.cellStyleFieldRightBold = xssfWorkbook.createCellStyle();
        this.cellStyleFieldCargo = xssfWorkbook.createCellStyle();
        this.cellStyleFieldNeedCalc = xssfWorkbook.createCellStyle();
        this.cellStyleFieldTotal = xssfWorkbook.createCellStyle();
        this.cellStyleFieldTotalFormat = xssfWorkbook.createCellStyle();
        this.cellStyleFieldTotalFormatDistance = xssfWorkbook.createCellStyle();
        this.cellStyleFieldTotalRight = xssfWorkbook.createCellStyle();
        this.xssfFont = xssfWorkbook.createFont();
        this.format = xssfWorkbook.createDataFormat();
        cellStyleField();
        cellStyleFieldFormat();
        cellStyleFieldFormatDistance();
        cellStyleHead();
        cellStyleHeadBottom();
        cellStyleHeadRight();
        cellStyleFieldNull();
        cellStyleFieldRightBold();
        cellStyleFieldCargo();
        cellStyleFieldNeedCalc();
        cellStyleFieldTotal();
        cellStyleFieldTotalFormat();
        cellStyleFieldTotalFormatDistance();
        cellStyleFieldTotalRight();
        getFont();
    }

    private void getFont() {
        xssfFont.setFontName("Times New Roman");
        xssfFont.setFontHeight(12.0);
    }

    private short formatWith00() {
        return format.getFormat("#,##0.00");
    }

    private short formatWithOut00() {
        return format.getFormat("#,##0");
    }

    private void cellStyleField() {
        cellStyleField.setFont(xssfFont);
        cellStyleField.setBorderBottom(BorderStyle.DOTTED);
        cellStyleField.setBorderTop(BorderStyle.DOTTED);
        cellStyleField.setBorderRight(BorderStyle.DOTTED);
        cellStyleField.setBorderLeft(BorderStyle.DOTTED);
        cellStyleField.setAlignment(HorizontalAlignment.CENTER);
        cellStyleField.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    private void cellStyleFieldFormat() {
        cellStyleFieldFormat.setFont(xssfFont);
        cellStyleFieldFormat.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldFormat.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldFormat.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldFormat.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldFormat.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldFormat.setWrapText(true);
        cellStyleFieldFormat.setDataFormat(formatWith00());
    }

    private void cellStyleFieldFormatDistance() {
        cellStyleFieldFormatDistance.setFont(xssfFont);
        cellStyleFieldFormatDistance.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldFormatDistance.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldFormatDistance.setWrapText(true);
        cellStyleFieldFormatDistance.setDataFormat(formatWithOut00());
    }

    private void cellStyleHead() {
        cellStyleHead.setFont(xssfFont);
        cellStyleHead.setBorderRight(BorderStyle.DOTTED);
        cellStyleHead.setBorderLeft(BorderStyle.DOTTED);
        cellStyleHead.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHead.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHead.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153)));
        cellStyleHead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHead.setWrapText(true);
    }

    private void cellStyleHeadBottom() {
        cellStyleHeadBottom.setFont(xssfFont);
        cellStyleHeadBottom.setBorderRight(BorderStyle.DOTTED);
        cellStyleHeadBottom.setBorderBottom(BorderStyle.DOTTED);
        cellStyleHeadBottom.setBorderLeft(BorderStyle.DOTTED);
        cellStyleHeadBottom.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHeadBottom.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHeadBottom.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153)));
        cellStyleHeadBottom.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeadBottom.setWrapText(true);
    }

    private void cellStyleHeadRight() {
        cellStyleHeadRight.setFont(xssfFont);
        cellStyleHeadRight.setBorderRight(BorderStyle.MEDIUM);
        cellStyleHeadRight.setBorderBottom(BorderStyle.DOTTED);
        cellStyleHeadRight.setBorderLeft(BorderStyle.DOTTED);
        cellStyleHeadRight.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHeadRight.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHeadRight.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153)));
        cellStyleHeadRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeadRight.setWrapText(true);
    }

    private void cellStyleFieldNull() {
        xssfFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        cellStyleFieldNull.setFont(xssfFont);
        cellStyleFieldNull.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldNull.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldNull.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldNull.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldNull.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldNull.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldNull.setWrapText(true);
    }

    private void cellStyleFieldRightBold() {
        xssfFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        cellStyleFieldRightBold.setFont(xssfFont);
        cellStyleFieldRightBold.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldRightBold.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldRightBold.setBorderRight(BorderStyle.MEDIUM);
        cellStyleFieldRightBold.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldRightBold.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldRightBold.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldRightBold.setWrapText(true);
    }

    private void cellStyleFieldCargo() {
        cellStyleFieldCargo.setFont(xssfFont);
        cellStyleFieldCargo.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldCargo.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldCargo.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldCargo.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldCargo.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    private void cellStyleFieldNeedCalc() {
        xssfFont.setBold(true);
        cellStyleFieldNeedCalc.setFont(xssfFont);
        cellStyleFieldNeedCalc.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldNeedCalc.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldNeedCalc.setFillForegroundColor(new XSSFColor(new Color(214, 214, 214)));
        cellStyleFieldNeedCalc.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldNeedCalc.setDataFormat(formatWith00());
    }

    private void cellStyleFieldTotal() {
        xssfFont.setBold(false);
        cellStyleFieldTotal.setFont(xssfFont);
        cellStyleFieldTotal.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotal.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotal.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldTotal.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotal.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotal.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotal.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyleFieldTotal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotal.setWrapText(true);
    }

    private void cellStyleFieldTotalFormat() {
        cellStyleFieldTotalFormat.setFont(xssfFont);
        cellStyleFieldTotalFormat.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotalFormat.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotalFormat.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldTotalFormat.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotalFormat.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotalFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotalFormat.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyleFieldTotalFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotalFormat.setWrapText(true);
        cellStyleFieldTotalFormat.setDataFormat(formatWith00());
    }

    private void cellStyleFieldTotalFormatDistance() {
        cellStyleFieldTotalFormatDistance.setFont(xssfFont);
        cellStyleFieldTotalFormatDistance.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotalFormatDistance.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotalFormatDistance.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldTotalFormatDistance.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotalFormatDistance.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotalFormatDistance.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotalFormatDistance.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyleFieldTotalFormatDistance.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotalFormatDistance.setWrapText(true);
        cellStyleFieldTotalFormatDistance.setDataFormat(formatWithOut00());
    }

    private void cellStyleFieldTotalRight() {
        cellStyleFieldTotalRight.setFont(xssfFont);
        cellStyleFieldTotalRight.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotalRight.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotalRight.setBorderRight(BorderStyle.MEDIUM);
        cellStyleFieldTotalRight.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotalRight.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotalRight.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotalRight.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyleFieldTotalRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotalRight.setWrapText(true);
        cellStyleFieldTotalRight.setDataFormat(formatWith00());
    }
}
