package com.example.demo.exception.handler;

import com.example.demo.exception.RestTemplateErrorException;
import com.example.demo.exception.error.CustomError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestTemplateErrorHandlerTest {

    @Test
    public void testHandleError_ClientError() throws Exception {
        // Arrange
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        when(response.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(response.getBody()).thenReturn(new ByteArrayInputStream("Client Error".getBytes()));

        // Act & Assert
        RestTemplateErrorException thrownException = assertThrows(RestTemplateErrorException.class, () -> {
            errorHandler.handleError(response);
        });

        CustomError expectedError = CustomError.builder()
                .time(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.API_ERROR.getName())
                .message("Client Error")
                .build();

        // Verify that the exception thrown contains the expected values
        assertEquals(expectedError.getHttpStatus(), thrownException.getCustomError().getHttpStatus());
        assertEquals(expectedError.getMessage(), thrownException.getCustomError().getMessage());
    }

    @Test
    public void testHandleError_ServerError() throws Exception {
        // Arrange
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(response.getBody()).thenReturn(new ByteArrayInputStream("Server Error".getBytes()));

        // Act & Assert
        RestTemplateErrorException thrownException = assertThrows(RestTemplateErrorException.class, () -> {
            errorHandler.handleError(response);
        });

        CustomError expectedError = CustomError.builder()
                .time(LocalDateTime.now())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(CustomError.Header.API_ERROR.getName())
                .message("Server Error")
                .build();

        // Verify that the exception thrown contains the expected values
        assertEquals(expectedError.getHttpStatus(), thrownException.getCustomError().getHttpStatus());
        assertEquals(expectedError.getMessage(), thrownException.getCustomError().getMessage());
    }

    @Test
    public void testHasError_ClientError() throws IOException {
        // Arrange
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        when(response.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);

        // Act
        boolean hasError = errorHandler.hasError(response);

        // Assert
        assertTrue(hasError);
    }

    @Test
    public void testHasError_ServerError() throws IOException {
        // Arrange
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

        // Act
        boolean hasError = errorHandler.hasError(response);

        // Assert
        assertTrue(hasError);
    }

    @Test
    public void testHasError_NoError() throws IOException {
        // Arrange
        RestTemplateErrorHandler errorHandler = new RestTemplateErrorHandler();
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        when(response.getStatusCode()).thenReturn(HttpStatus.OK);

        // Act
        boolean hasError = errorHandler.hasError(response);

        // Assert
        assertFalse(hasError);
    }
}
