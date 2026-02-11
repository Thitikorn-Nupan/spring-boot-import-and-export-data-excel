package com.ttknp.understandspringbootapplyexceldata.services;

import com.ttknp.understandspringbootapplyexceldata.entities.Province;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProvinceService {

    private static final Logger log = LoggerFactory.getLogger(ProvinceService.class);
    private XSSFSheet xssfSheet;
    private List<Province> provinces;

    public ProvinceService(String pathToExcel) {
        loadProvincesFormExcelAbsOrRootPath(pathToExcel);
    }

    private void loadProvincesFormExcelAbsOrRootPath(String pathToExcel) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(new File(pathToExcel));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Workbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Sheet sheet = xssfWorkbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        provinces = new ArrayList<>();
        for (int n = 1; n < sheet.getPhysicalNumberOfRows(); n++) {
            Row row = sheet.getRow(n);
            Province province = new Province();
            int i = row.getFirstCellNum();
            // log.debug("row.getCell(0) {}", row.getCell(i).getNumericCellValue());
            province.setNo((int) row.getCell(i++).getNumericCellValue());
            // i++;
            province.setNameEn(dataFormatter.formatCellValue(row.getCell(i++)));
            // log.debug("row.getCell(1) {}", dataFormatter.formatCellValue(row.getCell(i)));
            // i++;
            province.setNameTh(dataFormatter.formatCellValue(row.getCell(i++)));
            // log.debug("row.getCell(2) {}", dataFormatter.formatCellValue(row.getCell(i)));
            // i++;
            province.setPopulation((long) row.getCell(i++).getNumericCellValue());
            // log.debug("row.getCell(3) {}", row.getCell(i).getNumericCellValue());
            // i++;
            province.setArea((int) row.getCell(i++).getNumericCellValue());
            // log.debug("row.getCell(4) {}", row.getCell(i).getNumericCellValue());
            provinces.add(province);
        }
    }

    public List<Province> getProvincesFromExcel() {
        return provinces;
    }

    public Boolean saveProvincesToExcelAbsOrRootPath(String pathToExcel) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        writeHeaderLine(xssfWorkbook);
        writeDataLines(xssfWorkbook);

        log.debug("write header and data lines");
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(new File(pathToExcel));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            xssfWorkbook.write(file);
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
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    // writeHeaderLine() for writing header line
    private void writeHeaderLine(XSSFWorkbook xssfWorkbook) {
        xssfSheet = xssfWorkbook.createSheet("Provinces");
        Row row = xssfSheet.createRow(0);
        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "# (No)", style);
        createCell(row, 1, "Province Name (EN)", style);
        createCell(row, 2, "Province Name (TH)", style);
        createCell(row, 3, "Population (Around)", style);
        createCell(row, 4, "Area (Km)", style);
    }

    // writeDataLines() for writing data line
    private void writeDataLines(XSSFWorkbook xssfWorkbook) {
        int rowCount = 1;
        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Province province : provinces) {
            Row row = xssfSheet.createRow(rowCount++); // create starts from row 2 cause row 1 is headers
            int columnCount = 0; // it starts on cell 1
            createCell(row, columnCount++, province.getNo(), style); // cell 1
            createCell(row, columnCount++, province.getNameEn(), style); // cell 2
            createCell(row, columnCount++, province.getNameTh(), style); // cell 3
            createCell(row, columnCount++, province.getPopulation(), style); // cell 4
            createCell(row, columnCount++, province.getArea(), style); // cell 5
        }
    }
}
