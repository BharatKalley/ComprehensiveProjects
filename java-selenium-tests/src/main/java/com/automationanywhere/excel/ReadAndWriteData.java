package com.automationanywhere.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class ReadAndWriteData {

    private static final Logger logger = LogManager.getLogger(ReadAndWriteData.class);

    public static void main(String[] args) {
        // Use classpath resource loading
        String excelFilePath = "/testdata.xlsx";  // No need for src/main/resources in path
        List<Map<String, String>> excelData = new ArrayList<>();

        try (InputStream fis = ReadAndWriteData.class.getResourceAsStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            if (fis == null) {
                throw new FileNotFoundException("Resource not found: " + excelFilePath);
            }

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> rowData = new HashMap<>();
                Row row = sheet.getRow(i);

                for (int j = 0; j < columnCount; j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = row.getCell(j).getStringCellValue();
                    rowData.put(key, value);
                }

                excelData.add(rowData);
            }

            for (Map<String, String> entry : excelData) {
                logger.info(entry);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
