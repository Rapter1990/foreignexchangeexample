package com.example.demo.service.impl;

import com.example.demo.base.AbstractBaseServiceTest;
import com.example.demo.exception.ExchangeNotFoundException;
import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.model.mapper.ExchangeResponseToExchangeRateMapper;
import com.example.demo.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExchangeRateServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private ExchangeService exchangeService;

    private final ExchangeResponseToExchangeRateMapper exchangeResponseToExchangeRateMapper =
            ExchangeResponseToExchangeRateMapper.initialize();

    @Test
    public void testExchangeRate() {

        // Given
        ExchangeRateRequest request = ExchangeRateRequest.builder()
                .from("USD")
                .to("EUR")
                .build();

        ExchangeResponse exchangeResponse = ExchangeResponse.builder()
                .result(new BigDecimal("85.00"))
                .success(true)
                .build();

        ExchangeRate expectedExchangeRate = exchangeResponseToExchangeRateMapper.map(exchangeResponse);


        // When
        when(exchangeService.getExchangeRateWithAmount(request.getFrom(), request.getTo(), BigDecimal.valueOf(1)))
                .thenReturn(exchangeResponse);

        // Then
        ExchangeRate result = exchangeRateService.exchangeRate(request);

        assertNotNull(result);
        assertEquals(expectedExchangeRate.getResult(), result.getResult());

        // Verify
        verify(exchangeService).getExchangeRateWithAmount(request.getFrom(), request.getTo(), BigDecimal.valueOf(1));

    }

    @Test
    public void testExchangeRate_ExchangeNotFound() {

        // Given
        ExchangeRateRequest request = ExchangeRateRequest.builder()
                .from("USD")
                .to("EUR")
                .build();

        // When
        when(exchangeService.getExchangeRateWithAmount(request.getFrom(), request.getTo(), BigDecimal.valueOf(1)))
                .thenThrow(new ExchangeNotFoundException("Exchange rate not found for USD to EUR"));

        // Throw
        assertThrows(ExchangeNotFoundException.class, () -> {
            exchangeRateService.exchangeRate(request);
        });

        // Verify
        verify(exchangeService).getExchangeRateWithAmount(request.getFrom(), request.getTo(), BigDecimal.valueOf(1));

    }

}