package com.example.demo.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents an exchange rate result.
 * This class holds the result of an exchange rate calculation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    private BigDecimal result;
}
