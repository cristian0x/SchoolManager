package com.springbootschoolmanager.exception._class;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ClassAlreadyExistsException extends RuntimeException {

    public ClassAlreadyExistsException(final String message) {
        super(message);
    }
}
