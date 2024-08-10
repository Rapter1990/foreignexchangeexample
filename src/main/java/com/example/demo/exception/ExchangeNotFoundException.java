package com.example.demo.exception;

import java.io.Serial;

/**
 * Exception thrown when an exchange is not found.
 * This exception is used to indicate that a specific exchange could not be found
 * within the system. It extends {@link RuntimeException} and can be used to provide
 * a custom error message when an exchange-related operation fails due to the exchange
 * being absent.
 */
public class ExchangeNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1413798248660284552L;

    /**
     * Constructs a new {@code ExchangeNotFoundException} with the specified detail message.
     * The detail message is a {@code String} that provides additional information about
     * the error and will be included in the exception's {@code getMessage()} method.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ExchangeNotFoundException (final String message) {
        super(message);
    }

}
