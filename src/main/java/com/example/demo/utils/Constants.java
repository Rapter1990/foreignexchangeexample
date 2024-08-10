package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A component class for managing application-wide constants related to the exchange API.
 * This class holds static constants that are used throughout the application for interacting
 * with the exchange API. The values are injected from the application properties using
 * Spring's `@Value` annotation.
 */
@Component
public class Constants {

    public static String EXCHANGE_API_BASE_URL;
    public static String EXCHANGE_API_API_KEY;

    public static String EXCHANGE_CACHE_NAME;
    public static Integer EXCHANGE_API_CALL_LIMIT;

    /**
     * Sets the base URL of the exchange API.
     * This method is used by Spring to inject the value of the `exchange-api.api-url` property
     * from the application properties file into the static field {@link #EXCHANGE_API_BASE_URL}.
     *
     * @param apiUrl The base URL of the exchange API.
     */
    @Value("${exchange-api.api-url}")
    public void setExchangeApiBaseUrl(String apiUrl) {
        EXCHANGE_API_BASE_URL = apiUrl;
    }

    /**
     * Sets the API key used for authenticating requests to the exchange API.
     * This method is used by Spring to inject the value of the `exchange-api.api-key` property
     * from the application properties file into the static field {@link #EXCHANGE_API_API_KEY}.
     *
     * @param apiKey The API key for the exchange API.
     */
    @Value("${exchange-api.api-key}")
    public void setExchangeApiKey(String apiKey) {
        EXCHANGE_API_API_KEY = apiKey;
    }

    /**
     * Sets the name of the cache used for storing exchange data.
     * This method is used by Spring to inject the value of the `exchange-api.cache-name` property
     * from the application properties file into the static field {@link #EXCHANGE_CACHE_NAME}.
     *
     * @param cacheName The name of the cache for exchange data.
     */
    @Value("${exchange-api.cache-name}")
    public void setExchangeCacheName(String cacheName) {
        EXCHANGE_CACHE_NAME = cacheName;
    }

    /**
     * Sets the limit on the number of API calls allowed to the exchange API.
     * This method is used by Spring to inject the value of the `exchange-api.api-call-limit` property
     * from the application properties file into the static field {@link #EXCHANGE_API_CALL_LIMIT}.
     *
     * @param apiCallLimit The limit on the number of API calls.
     */
    @Value("${exchange-api.api-call-limit}")
    public void setExchangeApiCallLimit(Integer apiCallLimit) {
        EXCHANGE_API_CALL_LIMIT = apiCallLimit;
    }

}
