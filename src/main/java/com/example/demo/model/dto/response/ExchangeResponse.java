package com.example.demo.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {
        private String from;
        private String to;
        private BigDecimal amount;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info {
        private BigDecimal quote;
    }
}
