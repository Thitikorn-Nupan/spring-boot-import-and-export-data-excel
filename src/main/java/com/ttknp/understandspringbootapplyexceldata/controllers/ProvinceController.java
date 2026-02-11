package com.ttknp.understandspringbootapplyexceldata.controllers;

import com.ttknp.understandspringbootapplyexceldata.entities.Province;
import com.ttknp.understandspringbootapplyexceldata.services.ProvinceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/province")
public class ProvinceController {

    private final ProvinceService provinceService;
    // can use abs path
    private final String rootExcelPath = "src/main/resources/excel";

    public ProvinceController() {
        this.provinceService = new ProvinceService(rootExcelPath+"/provinces_of_thailand.xlsx");
    }

    @GetMapping(value = "/reads")
    public @ResponseBody @ResponseStatus(code = HttpStatus.OK) List<Province> reads() {
        return this.provinceService.getProvincesFromExcel();
    }

    @GetMapping(value = "/save")
    public @ResponseBody @ResponseStatus(code = HttpStatus.OK) Boolean save() {
        return this.provinceService.saveProvincesToExcelAbsOrRootPath(rootExcelPath+"/provinces_of_thailand_v2.xlsx");
    }
}
