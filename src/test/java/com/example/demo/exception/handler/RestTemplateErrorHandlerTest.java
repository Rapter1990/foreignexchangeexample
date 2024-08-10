package com.example.demo.exception.handler;

import com.example.demo.exception.RestTemplateErrorException;
import com.example.demo.exception.error.CustomError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link RestTemplateErrorHandler} class.
 * This test class verifies the behavior of the {@link RestTemplateErrorHandler} when handling client and server errors,
 * as well as when determining the presence of errors in the HTTP response.
 */
class RestTemplateErrorHandlerTest {

    @Test
    @DisplayName("Given ClientError - When HandleError - Then Throw RestTemplateErrorException with Client Error")
    void givenClientError_whenHandleError_thenThrowRestTemplateErrorExceptionWithClientError() throws Exception {

        // Given
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        CustomError expectedError = CustomError.builder()
                .time(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.API_ERROR.getName())
                .message("Client Error")
                .build();

        // When
        when(response.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(response.getBody()).thenReturn(new ByteArrayInputStream("Client Error".getBytes()));

        // Then
        RestTemplateErrorException thrownException = assertThrows(RestTemplateErrorException.class, () -> {
            errorHandler.handleError(response);
        });

        assertEquals(expectedError.getHttpStatus(), thrownException.getCustomError().getHttpStatus());
        assertEquals(expectedError.getMessage(), thrownException.getCustomError().getMessage());

    }

    @Test
    @DisplayName("Given ServerError - When HandleError - Then Throw RestTemplateErrorException with Server Error")
    void givenServerError_whenHandleError_thenThrowRestTemplateErrorExceptionWithServerError() throws Exception {

        // Given
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        CustomError expectedError = CustomError.builder()
                .time(LocalDateTime.now())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(CustomError.Header.API_ERROR.getName())
                .message("Server Error")
                .build();

        // When
        when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(response.getBody()).thenReturn(new ByteArrayInputStream("Server Error".getBytes()));

        // Then
        RestTemplateErrorException thrownException = assertThrows(RestTemplateErrorException.class, () -> {
            errorHandler.handleError(response);
        });

        assertEquals(expectedError.getHttpStatus(), thrownException.getCustomError().getHttpStatus());
        assertEquals(expectedError.getMessage(), thrownException.getCustomError().getMessage());

    }

    @Test
    @DisplayName("Given ClientError - When HasError - Then Return True")
    void givenClientError_whenHasError_thenReturnTrue() throws IOException {

        // Given
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        // When
        when(response.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);

        // Then
        boolean hasError = errorHandler.hasError(response);

        assertTrue(hasError);

    }

    @Test
    @DisplayName("Given ServerError - When HasError - Then Return True")
    void givenServerError_whenHasError_thenReturnTrue() throws IOException {

        // Given
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        // When
        when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

        // Then
        boolean hasError = errorHandler.hasError(response);

        assertTrue(hasError);

    }

    @Test
    @DisplayName("Given NoError - When HasError - Then Return False")
    void givenNoError_whenHasError_thenReturnFalse() throws IOException {

        // Given
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        // When
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);

        // Then
        boolean hasError = errorHandler.hasError(response);

        assertFalse(hasError);

    }

}
