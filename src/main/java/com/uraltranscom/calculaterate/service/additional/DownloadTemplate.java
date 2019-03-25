package com.uraltranscom.calculaterate.service.additional;

import com.uraltranscom.calculaterate.service.export.WriteToFileExcel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 15.03.2019
 */

public class DownloadTemplate {
    public static void getTemplateFile(HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "inline; filename=template.xlsx");
            response.setContentType("application/vnd.ms-excel");

            ClassLoader classLoader = WriteToFileExcel.class.getClassLoader();
            File file = new File(classLoader.getResource("template.xlsx").getFile());
            ServletOutputStream outputStream = response.getOutputStream();
            Files.copy(file.toPath(), outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
