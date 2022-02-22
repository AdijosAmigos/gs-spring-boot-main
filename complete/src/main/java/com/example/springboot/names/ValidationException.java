package com.example.springboot.names;

public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }

}
