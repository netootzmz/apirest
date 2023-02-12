package com.students.apirest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.apirest.models.dto.request.GradeDTO;
import com.students.apirest.models.generic.GenericResponse;
import com.students.apirest.models.report.StudentDTO;
import com.students.apirest.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/grade")
@Api(value = "Student API REST")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "Add a grade to a student")
    @PostMapping
    public GenericResponse saveGrade(@RequestBody GradeDTO grade) {

        return studentService.saveGrade(grade);
    }


    @ApiOperation(value = "Deletes a student's grade")
    @DeleteMapping("{id}")
    public GenericResponse deleteGrade(@PathVariable(value = "id") Integer id) {

        return studentService.deleteGrade(id);
    }

    @ApiOperation(value = "Update a student's grades")
    @PutMapping
    public GenericResponse updateGrade(@RequestBody GradeDTO grade) {

        return studentService.updateGrade(grade);
    }

    @ApiOperation(value = "Get a student's grades")
    @GetMapping("student/{id}")
    public GenericResponse getGradeByStundent(@PathVariable(value = "id") Integer id) {

        return studentService.getGradeByStudentId(id);
    }

    @ApiOperation(value = "Get a student's grades")
    @GetMapping("student/{id}/report")
    public ResponseEntity<byte[]> getGradeByStundentReport(@PathVariable(value = "id") Integer id) {

        return studentService.getGradeByStudentIdReport(id);
    }

}
