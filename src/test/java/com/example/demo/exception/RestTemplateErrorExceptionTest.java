package com.example.demo.exception;

import com.example.demo.exception.error.CustomError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test class for {@link RestTemplateErrorException}.
 * This class contains test cases to verify the correct behavior of the
 * {@link RestTemplateErrorException} class, specifically focusing on the
 * constructor and its interaction with {@link CustomError}.
 */
class RestTemplateErrorExceptionTest {

    @Test
    @DisplayName("Given CustomError - When Construct RestTemplateErrorException - Then Return Verify CustomError and Message")
    void givenCustomError_whenConstructRestTemplateErrorException_thenVerifyCustomErrorAndMessage() {

        // Given
        CustomError customError = CustomError.builder()
                .time(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.API_ERROR.getName())
                .message("An error occurred")
                .build();

        // When & Then
        RestTemplateErrorException exception = new RestTemplateErrorException(customError);

        assertEquals(customError.getMessage(), exception.getMessage());
        assertEquals(customError, exception.getCustomError());

    }

}