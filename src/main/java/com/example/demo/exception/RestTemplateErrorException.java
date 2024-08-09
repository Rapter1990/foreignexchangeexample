package com.example.demo.exception;

import com.example.demo.exception.error.CustomError;

import java.io.Serial;

public class RestTemplateErrorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5037730675640226667L;

    private final CustomError customError;

    public RestTemplateErrorException(final CustomError customError) {
        super(customError.getMessage());
        this.customError = customError;
    }

    public CustomError getCustomError() {
        return customError;
    }
}