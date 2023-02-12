package com.students.apirest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.apirest.models.Grade;
import com.students.apirest.models.dto.request.GradeDTO;
import com.students.apirest.models.generic.GenericResponse;
import com.students.apirest.models.report.StudentDTO;
import com.students.apirest.repository.GradeRepository;
import com.students.apirest.service.StudentService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

@Service("studentService")
public class StudentServiceI implements StudentService {

    @Autowired
    GradeRepository gradeRepository;

    @Override
    public GenericResponse saveGrade(GradeDTO grade) {

        if (!new Integer(0).equals(grade.getMatterId()) &&
                !new Integer(0).equals(grade.getStudentID()) &&
                !new Double(0).equals(grade.getGrade())) {

            Grade gradeE = new Grade();
            gradeE.setStudentId(grade.getStudentID());
            gradeE.setMatterId(grade.getMatterId());
            gradeE.setGrade(grade.getGrade());
            gradeE.setRegisterDate(new Date());
            gradeE = gradeRepository.save(gradeE);
            System.out.println("****** ID NEW " + gradeE.getId());

            return new GenericResponse("ok", "calificacion registrada");


        } else {
            return new GenericResponse("not", "Valores incompletos");
        }

    }

    @Override
    public GenericResponse deleteGrade(Integer id) {

        GenericResponse response = new GenericResponse("not", "Valores incompletos");

        if (!new Integer(0).equals(id)) {

            try {
                gradeRepository.deleteById(id);
                response.setSuccess("ok");
                response.setMsg("calificacion eliminida");
            } catch (EmptyResultDataAccessException ex) {
                response.setSuccess("not");
                response.setMsg("No existe registro.");
            }

        }

        return response;
    }

    @Override
    public GenericResponse updateGrade(GradeDTO grade) {

        GenericResponse response = new GenericResponse("not", "Valores incompletos");

        if (!new Integer(0).equals(grade.getMatterId()) &&
                !new Integer(0).equals(grade.getStudentID()) &&
                !new Double(0).equals(grade.getGrade())) {


            try {
                Grade gradeE = gradeRepository.getOne(grade.getGradeId());
                gradeE.setGrade(grade.getGrade());
                gradeE.setRegisterDate(new Date());
                gradeRepository.save(gradeE);

                response.setSuccess("ok");
                response.setMsg("calificacion actualizada.");


            } catch (EmptyResultDataAccessException ex) {
                response.setSuccess("not");
                response.setMsg("No existe registro.");
            }


        }

        return response;

    }

    @Override
    public GenericResponse getGrade(GradeDTO grade) {
        Grade gradeTmp = gradeRepository.findById(1).get();
        System.out.println("****** " + gradeTmp.getRegisterDate());
        return null;
    }

    @Override
    public GenericResponse getGradeByStudentId(Integer id) {

        GenericResponse response = new GenericResponse("ok", "consulta ejecutada correctamente");
        DecimalFormat df = new DecimalFormat("0.00");

        List<Map<String,Object>> data = gradeRepository.findGradesByStudent(id);

        Map<String,Object> avg = new HashMap<>();
        avg.put("promedio",df.format(gradeRepository.avgFromGrades(id)));
        data.add(avg);
        response.setData(data);

        return response;
    }

    @Override
    public ResponseEntity<byte[]> getGradeByStudentIdReport(Integer id) {
        try {
            List<Map<String, Object>> dataMap = gradeRepository.findGradesByStudent(id);
            List<StudentDTO> studentList = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();

            for(Map<String,Object> item : dataMap){
                studentList.add(mapper.convertValue(item, StudentDTO.class));
            }

            Map<String, Object> empParams = new HashMap<>();
            empParams.put("nameReport", "Academic Summary");
            empParams.put("studentData", new JRBeanCollectionDataSource(studentList));

            JasperPrint empReport = null;

            empReport = JasperFillManager.fillReport
                    (
                            JasperCompileManager.compileReport(
                                    ResourceUtils.getFile("classpath:students-grade.jrxml")
                                            .getAbsolutePath())
                            , empParams
                            , new JREmptyDataSource()
                    );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "student-details.pdf");

            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
        } catch (JRException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
