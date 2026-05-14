package com.ttknp.understandspringbootapplyexceldata.controllers;

import com.ttknp.understandspringbootapplyexceldata.entities.Student;
import com.ttknp.understandspringbootapplyexceldata.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/student")
public class StudentController {

    private final StudentService studentService;
    // can use abs path
    private final String rootExcelPath = "src/main/resources/excel";

    public StudentController() {
        this.studentService = new StudentService();
    }

    @GetMapping(value = "/save")
    public @ResponseBody @ResponseStatus(code = HttpStatus.OK) Boolean save() {
        return this.studentService.saveStudentToExcelAbsOrRootPath(rootExcelPath+"/students.xlsx");
    }

    @GetMapping(value = "/reads")
    public @ResponseBody @ResponseStatus(code = HttpStatus.OK) List<Student> reads() {
        return this.studentService.readStudentsFormExcelAbsOrRootPath(rootExcelPath+"/students.xlsx");
    }
}
