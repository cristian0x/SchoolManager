package com.springbootschoolmanager.exception._class;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClassNotFoundException extends RuntimeException {

    public ClassNotFoundException(final String message) {
        super(message);
    }
}
