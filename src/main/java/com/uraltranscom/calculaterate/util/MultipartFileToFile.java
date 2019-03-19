package com.uraltranscom.calculaterate.util;

/**
 *
 * Класс для конвертации из MultipartFile в File
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 13.06.2018
 *
 * 13.06.2018
 *   1. Версия 1.0
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MultipartFileToFile {
    private static Logger logger = LoggerFactory.getLogger(MultipartFileToFile.class);
    private static File convertToFile;

    public static File multipartToFile(File file) {
        try {
            convertToFile = new File(System.getProperty("java.io.tmpdir"), file.getName());
            convertToFile.createNewFile();
            FileInputStream fileInputStream = new FileInputStream(file);
            try(FileOutputStream fileOutputStream = new FileOutputStream(convertToFile)) {
                int c;
                while((c = fileInputStream.read()) != -1){
                    fileOutputStream.write((char) c);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка конвертации файла - {}", e.getMessage());
        }
        return convertToFile;
    }
}
