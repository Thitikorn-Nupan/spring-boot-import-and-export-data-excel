package com.ttknp.understandspringbootapplyexceldata.services;

import com.ttknp.understandspringbootapplyexceldata.entities.Teacher;
import com.ttknp.understandspringbootapplyexceldata.helper.DataToExcelHelper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);
    private final List<Teacher> teacherList;
    private ArrayList<Teacher> teachersFromExcel;
    private DataToExcelHelper<Teacher> dataToExcelHelper;

    public TeacherService() {
        try {
            this.teacherList = List.of(
                    new Teacher(1,"alex",23,"m",50000.2D,new Date()),
                    new Teacher(2,"milli",20,"f",75000D,new Date()),
                    new Teacher(3,"json",20,"m",35000D,new SimpleDateFormat("yyyy/M/d").parse("1995/2/11"))
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        dataToExcelHelper = new DataToExcelHelper(this.teacherList);
    }

    public Boolean saveTeacherToExcelAbsOrRootPath(String pathToExcel) {
        return dataToExcelHelper.saveDataToExcelAbsOrRootPath(pathToExcel);
    }

    public List<Teacher> readTeachersFormExcelAbsOrRootPath(String pathToExcel) {
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        // DataFormatter dataFormatter = new DataFormatter();
        teachersFromExcel = new ArrayList<>();
        for (int n = 1; n < sheet.getPhysicalNumberOfRows(); n++) {
            Row row = sheet.getRow(n);
            Teacher teacher = new Teacher();
            int i = row.getFirstCellNum();
            // log.debug("row.getCell(0) {}", row.getCell(i).getNumericCellValue());
            teacher.setId((int) row.getCell(i++).getNumericCellValue());
            // i++;
            teacher.setName(row.getCell(i++).getStringCellValue());
            // i++;
            teacher.setAge((int)row.getCell(i++).getNumericCellValue());
            // i++;
            teacher.setGender(row.getCell(i++).getStringCellValue());
            // i++;
            teacher.setSalary((double) row.getCell(i++).getNumericCellValue());
            // i++;
            try {
                Date birthday = formatter.parse(String.format(row.getCell(i++).getStringCellValue()));
                teacher.setBirthday(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            teachersFromExcel.add(teacher);
        }
        return teachersFromExcel;
    }
}
