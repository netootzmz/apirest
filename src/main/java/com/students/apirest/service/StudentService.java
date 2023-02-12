package com.students.apirest.service;

import com.students.apirest.models.dto.request.GradeDTO;
import com.students.apirest.models.generic.GenericResponse;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    /**
     *
     * @param grade
     * @return GenericResponse with the result
     */
    GenericResponse saveGrade(GradeDTO grade);

    /**
     * @param id
     * @return GenericResponse with the result
     */
    GenericResponse deleteGrade(Integer id);

    /**
     * @param grade
     * @return GenericResponse with the result
     */
    GenericResponse updateGrade(GradeDTO grade);

    /**
     * @param grade
     * @return GenericResponse with the result
     */
    GenericResponse getGrade(GradeDTO grade);

    /**
     * @param id
     * @return GenericResponse with the result
     */
    GenericResponse getGradeByStudentId(Integer id);

    ResponseEntity<byte[]> getGradeByStudentIdReport(Integer id);

}
