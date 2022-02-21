package com.example.springboot.names;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NameAlreadyExist extends RuntimeException{

    public NameAlreadyExist(String message) {
        super(message);
    }

    public NameAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public NameAlreadyExist() {
    }
}
