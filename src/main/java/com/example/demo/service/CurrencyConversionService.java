package com.example.demo.service;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConvertRequest;

/**
 * Service interface for performing currency conversions.
 */
public interface CurrencyConversionService {

    /**
     * Converts a specified amount of money from one currency to another.
     *
     * @param convertRequest An {@link ConvertRequest} containing details of the conversion request.
     * @return A {@link Convert} object representing the result of the currency conversion.
     */
    Convert convertCurrency(final ConvertRequest convertRequest);

}
