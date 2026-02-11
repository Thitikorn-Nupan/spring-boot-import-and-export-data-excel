package com.ttknp.understandspringbootapplyexceldata.services;

import com.ttknp.understandspringbootapplyexceldata.entities.Teacher;
import com.ttknp.understandspringbootapplyexceldata.helper.DataToExcelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);
    private final List<Teacher> teacherList;
    private DataToExcelHelper<Teacher> dataToExcelHelper;

    public TeacherService() {
        try {
            this.teacherList = List.of(
                    new Teacher(1,"alex",23,"m",50000.2D,new Date()),
                    new Teacher(2,"milli",25,"f",75000D,new Date()),
                    new Teacher(3,"json",23,"m",35000D,new SimpleDateFormat("yyyy/M/d").parse("1995/2/11"))
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        dataToExcelHelper = new DataToExcelHelper(this.teacherList);
    }

    public Boolean saveTeacherToExcelAbsOrRootPath(String pathToExcel) {
        return dataToExcelHelper.saveDataToExcelAbsOrRootPath(pathToExcel);
    }
}
