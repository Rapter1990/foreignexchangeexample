package com.example.demo.exception;

import java.io.Serial;

public class ExchangeNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1413798248660284552L;

    public ExchangeNotFoundException (final String message) {
        super(message);
    }

}
