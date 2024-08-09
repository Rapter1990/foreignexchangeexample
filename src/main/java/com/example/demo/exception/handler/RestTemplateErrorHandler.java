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

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

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
