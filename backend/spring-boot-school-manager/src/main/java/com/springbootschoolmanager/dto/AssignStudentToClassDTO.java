package com.springbootschoolmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignStudentToClassDTO {

    @Pattern(regexp = "^[0-9]{11}")
    private String pesel;

    @Pattern(regexp = "^[0-9][a-z]$")
    private String class_id;
}
