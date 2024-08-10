package com.example.demo.exception;

import com.example.demo.exception.error.CustomError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestTemplateErrorExceptionTest {

    @Test
    public void testRestTemplateErrorException() {

        // Arrange
        CustomError customError = CustomError.builder()
                .time(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.API_ERROR.getName())
                .message("An error occurred")
                .build();

        // Act
        RestTemplateErrorException exception = new RestTemplateErrorException(customError);

        // Assert
        assertEquals(customError.getMessage(), exception.getMessage());
        assertEquals(customError, exception.getCustomError());

    }
}