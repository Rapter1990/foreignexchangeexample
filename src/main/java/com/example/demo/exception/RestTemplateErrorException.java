package com.example.demo.exception;

import com.example.demo.exception.error.CustomError;

import java.io.Serial;

/**
 * Exception thrown when a RestTemplate error occurs, encapsulating detailed error information.
 * This exception extends {@link RuntimeException} and is used to wrap a {@link CustomError}
 * object that contains detailed information about an error encountered during HTTP operations
 * with RestTemplate.
 */
public class RestTemplateErrorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5037730675640226667L;

    private final CustomError customError;

    /**
     * Constructs a new {@code RestTemplateErrorException} with the specified {@link CustomError}.
     * The {@code CustomError} object is used to provide detailed information about the error,
     * and its message will be used as the detail message for this exception.
     *
     * @param customError the custom error object containing details about the error
     */
    public RestTemplateErrorException(final CustomError customError) {
        super(customError.getMessage());
        this.customError = customError;
    }

    /**
     * Returns the {@link CustomError} associated with this exception.
     * The {@code CustomError} object provides detailed information about the error that occurred.
     *
     * @return the custom error object
     */
    public CustomError getCustomError() {
        return customError;
    }

}