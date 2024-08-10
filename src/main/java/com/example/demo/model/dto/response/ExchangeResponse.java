package com.example.demo.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents the response from an exchange rate service.
 * This class encapsulates the details returned by an API that provides exchange rates,
 * including the success status, query details, information about the quote, the result,
 * and the timestamp of the response.
 */
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeResponse {

    private boolean success;

    private Query query;

    private Info info;

    private BigDecimal result;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    /**
     * Represents the details of the query made to retrieve exchange rates.
     * This static inner class contains information about the source and target currencies,
     * as well as the amount to be converted.
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        private String from;
        private String to;
        private BigDecimal amount;
    }

    /**
     * Contains information about the exchange rate quote.
     * This static inner class includes details such as the rate at which one currency is converted to another.
     */
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info {
        private BigDecimal quote;
    }

}
