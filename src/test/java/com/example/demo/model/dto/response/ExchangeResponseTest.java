package com.example.demo.model.dto.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
class ExchangeResponseTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        ExchangeResponse.Query query = new ExchangeResponse.Query();
        query.setFrom("USD");
        query.setTo("EUR");
        query.setAmount(BigDecimal.valueOf(100));

        ExchangeResponse.Info info = new ExchangeResponse.Info();
        info.setQuote(BigDecimal.valueOf(0.85));

        LocalDateTime timestamp = LocalDateTime.now();

        // Create an instance of ExchangeResponse and set its fields
        ExchangeResponse response = ExchangeResponse.builder()
                .success(true)
                .query(query)
                .info(info)
                .result(BigDecimal.valueOf(85))
                .timestamp(timestamp)
                .build();

        // Act & Assert
        // Test success field
        assertEquals(true, response.isSuccess());
        // Test query fields
        assertEquals("USD", response.getQuery().getFrom());
        assertEquals("EUR", response.getQuery().getTo());
        assertEquals(BigDecimal.valueOf(100), response.getQuery().getAmount());
        // Test info field
        assertEquals(BigDecimal.valueOf(0.85), response.getInfo().getQuote());
        // Test result field
        assertEquals(BigDecimal.valueOf(85), response.getResult());
        // Test timestamp field
        assertEquals(timestamp, response.getTimestamp());
    }

}