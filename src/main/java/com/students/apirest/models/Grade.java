package com.students.apirest.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "t_calificaciones")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_t_calificaciones")
    private Integer id;

    @Column(name = "id_t_materias")
    private Integer matterId;

    @Column(name = "id_t_usuarios")
    private Integer studentId;
    @Column(name = "calificacion")
    private Double grade;
    @Column(name = "fecha_registro")
    private Date registerDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Grade grade = (Grade) o;
        return id != null && Objects.equals(id, grade.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
