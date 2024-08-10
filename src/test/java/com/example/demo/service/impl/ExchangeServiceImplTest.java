package com.example.demo.service.impl;

import com.example.demo.base.AbstractBaseServiceTest;
import com.example.demo.exception.ExchangeNotFoundException;
import com.example.demo.model.dto.response.ExchangeResponse;
import org.junit.jupiter.api.DisplayName;
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

/**
 * Unit test class for {@link ExchangeServiceImpl}.
 * This class contains test cases for verifying the behavior of the
 * {@link ExchangeServiceImpl} class, specifically focusing on methods
 * related to fetching exchange rates and constructing URLs for API calls.
 */
class ExchangeServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Given Valid Parameters - When Exchange Rate is Requested - Then Return Correct Exchange Rate")
    public void givenValidParameters_whenExchangeRateRequested_thenReturnCorrectExchangeRate() {

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

        // Then
        ExchangeResponse response = exchangeService.getExchangeRateWithAmount(from, to, amount);

        assertEquals(mockResponse, response);

        // Verify
        verify(restTemplate).getForObject(expectedUrl, ExchangeResponse.class);

    }

    @Test
    @DisplayName("Given Valid Parameters - When Exchange Rate is Not Found - Then Throw ExchangeNotFoundException")
    public void givenValidParameters_whenExchangeRateNotFound_thenThrowExchangeNotFoundException() {

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
    @DisplayName("Given Parameters - When Get Exchange URL is Invoked - Then Return Correct URL")
    public void givenParameters_whenGetExchangeUrlIsInvoked_thenReturnCorrectUrl() {

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