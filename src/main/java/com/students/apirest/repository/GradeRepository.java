package com.students.apirest.repository;

import com.students.apirest.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Query(value = "select " +
            "    ta.id_t_usuarios as id_t_usuario, " +
            "    ta.nombre as nombre, " +
            "    ta.ap_paterno as apellido, " +
            "    tm.nombre as materia, " +
            "    tc.calificacion as calificacion, " +
            "to_char(tc.fecha_registro, 'DD-MM-YYYY') as fecha_registro " +
            "from t_alumnos ta " +
            "inner join t_calificaciones tc on ta.id_t_usuarios = tc.id_t_usuarios " +
            "inner join t_materias tm on tm.id_t_materias = tc.id_t_materias " +
            "where ta.id_t_usuarios = :studentId",
    nativeQuery = true)
    List<Map<String,Object>> findGradesByStudent(@Param("studentId") Integer studentId);

    @Query(value = "select COALESCE(avg(t.calificacion),0) " +
            "from t_calificaciones t " +
            "where t.id_t_usuarios = :studentId",
            nativeQuery = true)
   Double avgFromGrades(@Param("studentId") Integer studentId);
}
