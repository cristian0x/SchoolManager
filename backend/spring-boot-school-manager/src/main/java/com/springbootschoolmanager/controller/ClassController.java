package com.springbootschoolmanager.controller;

import com.springbootschoolmanager.entity._Class;
import com.springbootschoolmanager.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @GetMapping
    public List<_Class> getAllClasses() {
        return classService.getAllClasses();
    }

    @PostMapping
    public ResponseEntity<?> createClass(@Valid @RequestBody final _Class _class) {
        classService.createClass(_class);
        return ResponseEntity.status(HttpStatus.CREATED).body("Class created successfully");
    }
}
