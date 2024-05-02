package com.mysite.sbb2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFormatException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public DataNotFormatException(String message) {
        super(message);
    }
}
