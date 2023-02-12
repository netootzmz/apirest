package com.students.apirest.models.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GradeDTO {
    @ApiModelProperty(notes = "grade ID", example = "1")
    private int gradeId;
    @ApiModelProperty(notes = "matter ID", example = "1")
    private int matterId;
    @ApiModelProperty(notes = "Student ID", example = "1")
    private int studentID;
    @ApiModelProperty(notes = "grade obtained during the course", example = "7.0")
    private double grade;

}
