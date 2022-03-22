package com.springbootschoolmanager.exception;

import com.springbootschoolmanager.exception._class.ClassAlreadyExistsException;
import com.springbootschoolmanager.exception.student.StudentAlreadyExistsException;
import com.springbootschoolmanager.exception.student.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {StudentNotFoundException.class, ClassNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> resourceNotFoundException (RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = {StudentAlreadyExistsException.class, ClassAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> resourceAlreadyExistsException (RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
