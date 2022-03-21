package com.springbootschoolmanager.exception.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class StudentAlreadyExistsException extends RuntimeException {

    public StudentAlreadyExistsException(final String message) {
        super(message);
    }
}
