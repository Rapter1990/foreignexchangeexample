package com.example.demo.service.impl;

import com.example.demo.base.AbstractBaseServiceTest;
import com.example.demo.exception.ExchangeNotFoundException;
import com.example.demo.model.dto.response.ExchangeResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static com.example.demo.utils.Constants.EXCHANGE_API_API_KEY;
import static com.example.demo.utils.Constants.EXCHANGE_API_BASE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExchangeServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGetExchangeRateWithAmount() {

        // Given
        String from = "USD";
        String to = "EUR";
        BigDecimal amount = new BigDecimal("100.00");

        ExchangeResponse mockResponse = ExchangeResponse.builder()
                .result(new BigDecimal("85.00"))
                .success(true)
                .build();

        String expectedUrl = String.format("%s/convert?access_key=%s&from=%s&to=%s&amount=%s",
                EXCHANGE_API_BASE_URL, EXCHANGE_API_API_KEY, from, to, amount);

        // When
        when(restTemplate.getForObject(expectedUrl, ExchangeResponse.class))
                .thenReturn(mockResponse);

        // When
        ExchangeResponse response = exchangeService.getExchangeRateWithAmount(from, to, amount);

        // Then
        assertEquals(mockResponse, response);

        // Verify
        verify(restTemplate).getForObject(expectedUrl, ExchangeResponse.class);

    }

    @Test
    public void testGetExchangeRateWithAmount_ExchangeNotFound() {

        // Given
        String from = "USD";
        String to = "EUR";
        BigDecimal amount = new BigDecimal("100.00");

        String expectedUrl = String.format("%s/convert?access_key=%s&from=%s&to=%s&amount=%s",
                EXCHANGE_API_BASE_URL, EXCHANGE_API_API_KEY, from, to, amount);

        // When
        when(restTemplate.getForObject(expectedUrl, ExchangeResponse.class))
                .thenReturn(null);

        // Throw
        assertThrows(ExchangeNotFoundException.class, () -> {
            exchangeService.getExchangeRateWithAmount(from, to, amount);
        });

        // Verify
        verify(restTemplate).getForObject(expectedUrl, ExchangeResponse.class);

    }

    @Test
    public void testGetExchangeUrl() {

        // Given
        String from = "USD";
        String to = "EUR";
        BigDecimal amount = new BigDecimal("100.00");

        // When
        String url = (String) ReflectionTestUtils.invokeMethod(exchangeService, "getExchangeUrl", from, to, amount);

        // Then
        String expectedUrl = String.format("%s/convert?access_key=%s&from=%s&to=%s&amount=%s",
                EXCHANGE_API_BASE_URL, EXCHANGE_API_API_KEY, from, to, amount);

        assertEquals(expectedUrl, url);

    }

}