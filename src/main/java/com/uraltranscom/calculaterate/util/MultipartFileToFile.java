package com.uraltranscom.calculaterate.util;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 13.06.2018
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MultipartFileToFile {
    private static Logger logger = LoggerFactory.getLogger(MultipartFileToFile.class);
    private static File convertToFile;

    public static File multipartToFile(MultipartFile file) {
        try {
            if (CheckEmptyParamConvertNull.checkEmptyParamConvert(file.getOriginalFilename()) == null) {
                return null;
            }
            convertToFile = new File(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
            convertToFile.createNewFile();
            try(FileOutputStream fileOutputStream = new FileOutputStream(convertToFile)) {
                fileOutputStream.write(file.getBytes());
            }
        } catch (IOException e) {
            logger.error("Ошибка конвертации файла - {}", e.getMessage());
        }
        return convertToFile;
    }
}
