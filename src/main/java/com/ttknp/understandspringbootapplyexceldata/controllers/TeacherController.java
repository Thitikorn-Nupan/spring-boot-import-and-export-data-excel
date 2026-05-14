package com.ttknp.understandspringbootapplyexceldata.controllers;

import com.ttknp.understandspringbootapplyexceldata.entities.Teacher;
import com.ttknp.understandspringbootapplyexceldata.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final String rootExcelPath = "src/main/resources/excel";

    public TeacherController() {
        this.teacherService = new TeacherService();
    }

    @GetMapping(value = "/save")
    public @ResponseBody @ResponseStatus(code = HttpStatus.OK) Boolean save() {
        return this.teacherService.saveTeacherToExcelAbsOrRootPath(rootExcelPath+"/teacher.xlsx");
    }

    @GetMapping(value = "/reads")
    public @ResponseBody @ResponseStatus(code = HttpStatus.OK) List<Teacher> reads() {
        return this.teacherService.readTeachersFormExcelAbsOrRootPath(rootExcelPath+"/teacher.xlsx");
    }
}
