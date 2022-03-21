package com.springbootschoolmanager.controller;

import com.springbootschoolmanager.dto.AssignStudentToClassDTO;
import com.springbootschoolmanager.entity.Student;
import com.springbootschoolmanager.exception._class.ClassNotFoundException;
import com.springbootschoolmanager.exception.student.StudentAlreadyExistsException;
import com.springbootschoolmanager.exception.student.StudentNotFoundException;
import com.springbootschoolmanager.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(@Pattern(regexp = "^[0-9][a-z]$")
                                                @RequestParam(required = false) final String class_id) {
        if (class_id == null) {
            return ResponseEntity.ok(studentService.getAllStudents());
        } else {
            return ResponseEntity.ok(studentService.getAllStudentsByClassId(class_id));
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody final Student student) {
        try {
            studentService.createStudent(student);
        } catch (final StudentAlreadyExistsException studentAlreadyExistsException){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(studentAlreadyExistsException.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Student created successfully");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStudent(@RequestParam final String pesel) {
        try {
            String response = studentService.deleteStudent(pesel);
            return ResponseEntity.ok(response);
        } catch (final StudentNotFoundException studentNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(studentNotFoundException.getMessage());
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignStudentToClass(@Valid @RequestBody final AssignStudentToClassDTO
                                                              assignStudentToClassDTO) {
        try {
            String response = studentService.assignStudentToClass(assignStudentToClassDTO.getClass_id(),
                    assignStudentToClassDTO.getPesel());
            return ResponseEntity.ok(response);
        } catch (final ClassNotFoundException | StudentNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }
}
