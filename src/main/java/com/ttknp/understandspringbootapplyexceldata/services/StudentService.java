package com.ttknp.understandspringbootapplyexceldata.services;

import com.ttknp.understandspringbootapplyexceldata.entities.Province;
import com.ttknp.understandspringbootapplyexceldata.entities.Student;
import com.ttknp.understandspringbootapplyexceldata.helper.DataToExcelHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final List<Student> studentList;
    private ArrayList<Student> studentsFormExcel;
    private DataToExcelHelper<Student> dataToExcelHelper;

    public StudentService() {
        this.studentList = List.of(
                new Student(1,"alex",21,"m"),
                new Student(2,"milli",21,"f"),
                new Student(3,"json",19,"m")
        );
        dataToExcelHelper = new DataToExcelHelper(this.studentList);
    }

    public Boolean saveStudentToExcelAbsOrRootPath(String pathToExcel) {
        return dataToExcelHelper.saveDataToExcelAbsOrRootPath(pathToExcel);
    }

    public List<Student> readStudentsFormExcelAbsOrRootPath(String pathToExcel) {
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
        // DataFormatter dataFormatter = new DataFormatter();
        studentsFormExcel = new ArrayList<>();
        for (int n = 1; n < sheet.getPhysicalNumberOfRows(); n++) {
            Row row = sheet.getRow(n);
            Student student = new Student();
            int i = row.getFirstCellNum();
            // log.debug("row.getCell(0) {}", row.getCell(i).getNumericCellValue());
            student.setId((int) row.getCell(i++).getNumericCellValue());
            // i++;
            student.setName(row.getCell(i++).getStringCellValue());
            // i++;
            student.setAge((int)row.getCell(i++).getNumericCellValue());
            // i++;
            student.setGender(row.getCell(i++).getStringCellValue());
            studentsFormExcel.add(student);
        }
        return studentsFormExcel;
    }

}
