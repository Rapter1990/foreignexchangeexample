package com.example.demo.model.mapper;

import com.example.demo.model.Convert;
import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.response.ExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link ExchangeRate} and {@link ExchangeRateResponse}.
 */
@Mapper
public interface ExchangeRateToExchangeRateResponseMapper extends BaseMapper<ExchangeRate, ExchangeRateResponse> {

    /**
     * Maps a {@link ExchangeRate} object to a {@link ExchangeRateResponse} object.
     *
     * @param source The {@link ExchangeRate} object to map.
     * @return A {@link ExchangeRateResponse} object mapped from the {@link Convert}.
     */
    @Override
    ExchangeRateResponse map(ExchangeRate source);

    /**
     * Initializes and returns an instance of {@code ConvertToConvertResponseMapper}.
     *
     * @return An initialized {@code ConvertToConvertResponseMapper} instance.
     */
    static ExchangeRateToExchangeRateResponseMapper initialize() {
        return Mappers.getMapper(ExchangeRateToExchangeRateResponseMapper.class);
    }
}
