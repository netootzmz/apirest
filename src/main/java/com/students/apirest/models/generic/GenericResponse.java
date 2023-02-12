package com.students.apirest.models.generic;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GenericResponse {

    private String success;
    private String msg;
    private List<Map<String,Object>> data;

    public GenericResponse(String success, String msg) {
        this.success = success;
        this.msg = msg;
    }

}
