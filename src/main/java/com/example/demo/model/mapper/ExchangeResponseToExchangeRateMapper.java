package com.example.demo.model.mapper;

import com.example.demo.model.Convert;
import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.model.entity.ConvertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link ExchangeResponse} and {@link ExchangeRate}.
 */
@Mapper
public interface ExchangeResponseToExchangeRateMapper extends BaseMapper<ExchangeResponse, ExchangeRate>{

    /**
     * Maps a {@link ExchangeResponse} object to a {@link ExchangeRate} object.
     *
     * @param source The {@link ExchangeResponse} object to map.
     * @return A {@link ExchangeRate} object mapped from the {@link Convert}.
     */
    @Mapping(source = "result", target = "result")
    @Override
    ExchangeRate map(ExchangeResponse source);

    /**
     * Initializes and returns an instance of {@code ExchangeResponseToExchangeRateMapper}.
     *
     * @return An initialized {@code ExchangeResponseToExchangeRateMapper} instance.
     */
    static ExchangeResponseToExchangeRateMapper initialize() {
        return Mappers.getMapper(ExchangeResponseToExchangeRateMapper.class);
    }

}
