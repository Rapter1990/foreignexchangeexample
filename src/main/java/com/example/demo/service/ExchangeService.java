package com.example.demo.service;

import com.example.demo.model.dto.response.ExchangeResponse;

import java.math.BigDecimal;

/**
 * Service interface for handling exchange rate operations.
 */
public interface ExchangeService {

    /**
     * Retrieves the exchange rate and converted amount between two currencies.
     *
     * @param from The currency code to convert from, in ISO 4217 format (e.g., "USD").
     * @param to The currency code to convert to, in ISO 4217 format (e.g., "EUR").
     * @param amount The amount of money to convert.
     * @return An {@link ExchangeResponse} containing the exchange rate and the converted amount.
     */
    ExchangeResponse getExchangeRateWithAmount(final String from, final String to, final BigDecimal amount);

}
