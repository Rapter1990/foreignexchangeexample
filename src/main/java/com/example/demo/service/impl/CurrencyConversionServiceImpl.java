package com.example.demo.service.impl;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.mapper.ConvertEntityToConvertMapper;
import com.example.demo.model.mapper.ExchangeResponseToConvertEntityMapper;
import com.example.demo.repository.ConvertRepository;
import com.example.demo.service.CurrencyConversionService;
import com.example.demo.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link CurrencyConversionService} interface.
 * Provides functionality for converting currencies by fetching exchange rates and saving conversion records.
 * Caches conversion results to enhance performance and avoid redundant calculations.
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "exchanges")
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final ConvertRepository convertRepository;

    private final ExchangeService exchangeService;

    private final ExchangeResponseToConvertEntityMapper exchangeResponseToConvertEntityMapper =
            ExchangeResponseToConvertEntityMapper.initialize();
    
    private final ConvertEntityToConvertMapper convertEntityToConvertMapper =
            ConvertEntityToConvertMapper.initialize();

    /**
     * Converts the specified amount from one currency to another.
     * This method retrieves the exchange rate from the {@link ExchangeService}, maps the response to a
     * {@link ConvertEntity} object, saves the conversion record to the repository, and then maps the saved
     * entity to a {@link Convert} object for return.
     *
     * @param request A {@link ConvertRequest} object containing the source currency, target currency, and the amount to convert.
     * @return A {@link Convert} object representing the result of the currency conversion.
     */
    @Override
    @Cacheable(key = "'CurrencyConversionCache::' + #request.from + '-' + #request.to + '-' + #request.amount")
    public Convert convertCurrency(final ConvertRequest request) {

        ExchangeResponse exchangeResponse = exchangeService.getExchangeRateWithAmount(
                request.getFrom(),request.getTo(),request.getAmount()
        );

        ConvertEntity convertEntityToBeSaved = exchangeResponseToConvertEntityMapper.map(exchangeResponse);

        ConvertEntity savedCovertEntity = convertRepository.save(convertEntityToBeSaved);

        return convertEntityToConvertMapper.map(savedCovertEntity);

    }
}
