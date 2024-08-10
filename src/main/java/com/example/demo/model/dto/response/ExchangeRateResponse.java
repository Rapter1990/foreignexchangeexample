package com.example.demo.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents the response containing the result of an exchange rate query.
 * This class encapsulates the result of an operation to retrieve the exchange rate between
 * two currencies, providing the rate as a {@link BigDecimal}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {
    private BigDecimal result;
}
