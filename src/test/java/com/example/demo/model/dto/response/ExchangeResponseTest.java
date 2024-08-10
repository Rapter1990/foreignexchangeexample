package com.example.demo.model.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test class for {@link ExchangeResponse}.
 * This class contains test cases to verify the correct behavior of the
 * getters and setters in the {@link ExchangeResponse} class, including
 * its nested static classes {@link ExchangeResponse.Query} and
 * {@link ExchangeResponse.Info}.
 */
class ExchangeResponseTest {

    @Test
    @DisplayName("Given ExchangeResponse Object - When Set Fields - Then Get Correct Values")
    void givenExchangeResponseObject_whenSetFields_thenGetCorrectValues() {

        // Given
        ExchangeResponse.Query query = new ExchangeResponse.Query();
        query.setFrom("USD");
        query.setTo("EUR");
        query.setAmount(BigDecimal.valueOf(100));

        ExchangeResponse.Info info = new ExchangeResponse.Info();
        info.setQuote(BigDecimal.valueOf(0.85));

        LocalDateTime timestamp = LocalDateTime.now();

        // When & Then
        ExchangeResponse response = ExchangeResponse.builder()
                .success(true)
                .query(query)
                .info(info)
                .result(BigDecimal.valueOf(85))
                .timestamp(timestamp)
                .build();

        assertTrue(response.isSuccess());
        assertEquals("USD", response.getQuery().getFrom());
        assertEquals("EUR", response.getQuery().getTo());
        assertEquals(BigDecimal.valueOf(100), response.getQuery().getAmount());
        assertEquals(BigDecimal.valueOf(0.85), response.getInfo().getQuote());
        assertEquals(BigDecimal.valueOf(85), response.getResult());
        assertEquals(timestamp, response.getTimestamp());

    }

}