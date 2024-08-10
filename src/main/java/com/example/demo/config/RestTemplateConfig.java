package com.example.demo.config;

import com.example.demo.exception.handler.RestTemplateErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up a RestTemplate bean with custom configuration.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates an instance of HttpComponentsClientHttpRequestFactory with a specified connection timeout.
     *
     * @return an instance of HttpComponentsClientHttpRequestFactory with custom configuration.
     */
    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {

        // Create an instance of Apache HttpClient
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();

        int connectTimeout = 5000;

        clientHttpRequestFactory.setConnectTimeout(connectTimeout);

        return clientHttpRequestFactory;
    }

    /**
     * Creates a RestTemplate bean with a custom request factory and error handler.
     *
     * @return a RestTemplate instance with custom configuration.
     */
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate;
    }

}
