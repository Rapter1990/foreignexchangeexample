package com.example.demo.exception.handler;

import com.example.demo.exception.RestTemplateErrorException;
import com.example.demo.exception.error.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Custom error handler for RestTemplate that processes client and server errors.
 * This implementation checks for HTTP status codes indicating client or server errors
 * and handles them by reading the error response body and throwing a custom exception
 * with details about the error.
 */
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    /**
     * Determines if the HTTP response indicates an error.
     * This implementation considers both 4xx client errors and 5xx server errors as errors.
     *
     * @param response the HTTP response to check
     * @return {@code true} if the response indicates an error; {@code false} otherwise
     * @throws IOException if an I/O error occurs while checking the response
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    /**
     * Handles the HTTP error response by reading the response body and throwing a custom exception.
     * This implementation reads the error response body, constructs a {@link CustomError} object
     * with details about the error, and throws a {@link RestTemplateErrorException} with that custom error.
     *
     * @param response the HTTP response to handle
     * @throws IOException if an I/O error occurs while reading the response body
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                String httpBodyResponse = reader.lines().collect(Collectors.joining(""));

                CustomError customError = CustomError.builder()
                        .time(LocalDateTime.now())
                        .httpStatus(HttpStatus.valueOf(response.getStatusCode().value()))
                        .header(CustomError.Header.API_ERROR.getName())
                        .message(httpBodyResponse)
                        .build();

                throw new RestTemplateErrorException(customError);

            }
        }
    }

}
