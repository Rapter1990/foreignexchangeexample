package com.example.demo.service;

import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.request.ExchangeRateRequest;

/**
 * Service interface for retrieving exchange rates.
 */
public interface ExchangeRateService {

    /**
     * Retrieves the exchange rate between two currencies based on the provided request.
     *
     * @param exchangeRateRequest An {@link ExchangeRateRequest} containing the currencies to convert between.
     * @return An {@link ExchangeRate} object containing the exchange rate.
     */
    ExchangeRate exchangeRate(final ExchangeRateRequest exchangeRateRequest);

}
