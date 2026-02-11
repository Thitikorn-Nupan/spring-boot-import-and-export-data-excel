package com.ttknp.understandspringbootapplyexceldata.services;

import com.ttknp.understandspringbootapplyexceldata.entities.Student;
import com.ttknp.understandspringbootapplyexceldata.helper.DataToExcelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final List<Student> studentList;
    private DataToExcelHelper<Student> dataToExcelHelper;

    public StudentService() {
        this.studentList = List.of(
                new Student(1,"alex",23,"m"),
                new Student(2,"milli",25,"f"),
                new Student(3,"json",23,"m")
        );
        dataToExcelHelper = new DataToExcelHelper(this.studentList);
    }

    public Boolean saveStudentToExcelAbsOrRootPath(String pathToExcel) {
        return dataToExcelHelper.saveDataToExcelAbsOrRootPath(pathToExcel);
    }
}
