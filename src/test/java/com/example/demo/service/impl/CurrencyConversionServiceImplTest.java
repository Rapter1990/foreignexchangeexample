package com.example.demo.service.impl;

import com.example.demo.base.AbstractBaseServiceTest;
import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.mapper.ConvertEntityToConvertMapper;
import com.example.demo.model.mapper.ExchangeResponseToConvertEntityMapper;
import com.example.demo.repository.ConvertRepository;
import com.example.demo.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrencyConversionServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private CurrencyConversionServiceImpl currencyConversionService;

    @Mock
    private ExchangeService exchangeService;

    @Mock
    private ConvertRepository convertRepository;

    private final ExchangeResponseToConvertEntityMapper exchangeResponseToConvertEntityMapper =
            ExchangeResponseToConvertEntityMapper.initialize();

    private final ConvertEntityToConvertMapper convertEntityToConvertMapper =
            ConvertEntityToConvertMapper.initialize();

    @Test
    public void testConvertCurrency() {

        // Given
        ConvertRequest request = ConvertRequest.builder()
                .from("USD")
                .to("EUR")
                .amount(new BigDecimal("100.00"))
                .build();

        ExchangeResponse exchangeResponse = ExchangeResponse.builder()
                .result(new BigDecimal("85.00"))
                .success(true)
                .build();

        ConvertEntity convertEntityToBeSaved = exchangeResponseToConvertEntityMapper.map(exchangeResponse);

        Convert expectedConvert = convertEntityToConvertMapper.map(convertEntityToBeSaved);

        // When
        when(exchangeService.getExchangeRateWithAmount(request.getFrom(), request.getTo(), new BigDecimal("100.00")))
                .thenReturn(exchangeResponse);

        when(convertRepository.save(any(ConvertEntity.class)))
                .thenReturn(convertEntityToBeSaved);


        // Then
        Convert actualConvert = currencyConversionService.convertCurrency(request);

        // Then
        assertEquals(expectedConvert.getTransactionId(), actualConvert.getTransactionId());
        assertEquals(expectedConvert.getFrom(), actualConvert.getFrom());
        assertEquals(expectedConvert.getTo(), actualConvert.getTo());
        assertEquals(expectedConvert.getAmount(), actualConvert.getAmount());
        assertEquals(expectedConvert.getConvertedAmount(), actualConvert.getConvertedAmount());

        // Verify
        verify(exchangeService).getExchangeRateWithAmount(request.getFrom(), request.getTo(), new BigDecimal("100.00"));
        verify(convertRepository).save(any(ConvertEntity.class));

    }

}