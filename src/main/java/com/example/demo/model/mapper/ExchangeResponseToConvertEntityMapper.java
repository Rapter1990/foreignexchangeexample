package com.example.demo.model.mapper;

import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.model.entity.ConvertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExchangeResponseToConvertEntityMapper extends BaseMapper<ExchangeResponse, ConvertEntity> {

    /**
     * Maps an {@link ExchangeResponse} object to a {@link ConvertEntity} object.
     *
     * @param source The {@link ExchangeResponse} object to map.
     * @return A {@link ConvertEntity} object mapped from the {@link ExchangeResponse}.
     */
    @Override
    @Mapping(source = "query.amount", target = "amount")
    @Mapping(source = "query.from", target = "fromCurrency")
    @Mapping(source = "query.to", target = "toCurrency")
    @Mapping(source = "result", target = "convertedAmount")
    @Mapping(source = "timestamp", target = "createdAt")
    ConvertEntity map(ExchangeResponse source);

    /**
     * Initializes and returns an instance of {@code ExchangeResponseToConvertEntityMapper}.
     *
     * @return An initialized {@code ExchangeResponseToConvertEntityMapper} instance.
     */
    static ExchangeResponseToConvertEntityMapper initialize() {
        return Mappers.getMapper(ExchangeResponseToConvertEntityMapper.class);
    }

}

