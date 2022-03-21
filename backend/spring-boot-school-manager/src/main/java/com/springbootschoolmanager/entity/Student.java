package com.springbootschoolmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(name = "pesel")
    @Pattern(regexp = "^[0-9]{11}")
    // ^[0-9]{2}([02468]1|[13579][012])(0[1-9]|1[0-9]|2[0-9]|3[01])[0-9]{5}$
    private String pesel;

    @Column(name = "first_name")
    @Size(min = 1, max = 64)
    private String first_name;

    @Column(name = "last_name")
    @Size(min = 1, max = 64)
    private String last_name;

    @Column(name = "class_id")
    @Pattern(regexp = "^[1-9][a-z]$")
    private String class_id;
}
