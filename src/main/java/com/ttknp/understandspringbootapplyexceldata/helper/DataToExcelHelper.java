package com.ttknp.understandspringbootapplyexceldata.helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataToExcelHelper<T> {

    private static final Logger log = LoggerFactory.getLogger(DataToExcelHelper.class);
    private final List<T> dataList;
    private final T data;
    private XSSFSheet xssfSheet;

    public DataToExcelHelper(List<T> dataList) {
        this.dataList = dataList;
        this.data = dataList.get(0);
    }

    // *** Write excel files
    // createCell(...) for specify type when writing header line
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        xssfSheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Date) {
            // 1. (Input Format)
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            // 2. String To ZonedDateTime
            ZonedDateTime zdt = ZonedDateTime.parse(value.toString(), inputFormatter);
            // 3. (Output Format: y/m/d)
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            cell.setCellValue(outputFormatter.format(zdt));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    // writeHeaderLine() for writing header line
    private void writeHeaderLine(XSSFWorkbook xssfWorkbook) {
        xssfSheet = xssfWorkbook.createSheet(data.getClass().getSimpleName());
        Row row = xssfSheet.createRow(0);
        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        Field[] fields = data.getClass().getDeclaredFields();
        int columnCount = 0;
        for (Field field : fields) {
            // Make private fields accessible
            field.setAccessible(true);
            String fieldName = field.getName();
            createCell(row, columnCount++, fieldName.toUpperCase(), style);
        }
    }

    // writeDataLines() for writing data line
    private void writeDataLines(XSSFWorkbook xssfWorkbook) {
        int rowCount = 1;
        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (T data : dataList) { // get data from data list
            // get the data
            Field field;
            Field[] fields = data.getClass().getDeclaredFields(); // get all props of data
            Row row = xssfSheet.createRow(rowCount++); // create starts from row 2 cause row 1 is headers
            for (int i = 0; i < fields.length; i++) { // create columns of row
                try {
                    field = fields[i];
                    field.setAccessible(true);
                    createCell(row, i, field.get(data), style); // cell 1,2,3,...
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public Boolean saveDataToExcelAbsOrRootPath(String pathToExcel) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        writeHeaderLine(xssfWorkbook);
        log.debug("write header lines to xssf workbook");
        writeDataLines(xssfWorkbook);
        log.debug("write data lines to xssf workbook");
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(new File(pathToExcel));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            xssfWorkbook.write(file);
            log.debug("write excel file successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            xssfWorkbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
