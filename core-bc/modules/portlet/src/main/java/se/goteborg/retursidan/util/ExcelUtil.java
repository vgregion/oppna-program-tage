package se.goteborg.retursidan.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Björk
 */
public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    public static InputStream exportToStream(Map<String, List<Object[]>> sheets) {

        Workbook wb = new XSSFWorkbook();

        for (Map.Entry<String, List<Object[]>> sheetEntry : sheets.entrySet()) {
            Sheet sheet = wb.createSheet(sheetEntry.getKey());

            int rownum = 0;
            for (Object[] array : sheetEntry.getValue()) {
                Row row = sheet.createRow(rownum++);

                int cellnum = 0;

                for (Object value : array) {
                    Cell cell = row.createCell(cellnum++);
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Number) {
                        cell.setCellValue(Double.parseDouble(value.toString()));
                    } else {
                        throw new RuntimeException("Unexpected type of " + value + ": " + value.getClass());
                    }
                }
            }
        }

        try {
            PipedOutputStream pos = new PipedOutputStream();

            new Thread(() -> {
                try {
                    wb.write(pos);
                    pos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();

            return new PipedInputStream(pos);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
