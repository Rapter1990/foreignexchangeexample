package com.example.demo.service.impl;

import com.example.demo.exception.ExchangeNotFoundException;
import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.utils.Constants.EXCHANGE_API_API_KEY;
import static com.example.demo.utils.Constants.EXCHANGE_API_BASE_URL;

/**
 * Implementation of the {@link ExchangeService} interface.
 * Provides functionality to fetch exchange rates and amounts from a remote exchange rate API.
 * Caches responses for exchange rate requests to improve performance and reduce redundant API calls.
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "exchanges")
public class ExchangeServiceImpl implements ExchangeService {

    private final RestTemplate restTemplate;

    /**
     * Retrieves the exchange rate and converted amount between two currencies.
     * This method constructs the request URL, makes an HTTP GET request to the exchange rate API,
     * and returns the exchange rate information. If no exchange rate data is found, an
     * {@link ExchangeNotFoundException} is thrown.
     *
     * @param from The currency code to convert from (e.g., "USD").
     * @param to The currency code to convert to (e.g., "EUR").
     * @param amount The amount of currency to convert.
     * @return An {@link ExchangeResponse} object containing the exchange rate and converted amount.
     * @throws ExchangeNotFoundException if the exchange rate cannot be retrieved for the specified currencies.
     */
    @Override
    @Cacheable(key = "'ExchangeServiceCache::' + #from + '-' + #to + '-' + #amount")
    public ExchangeResponse getExchangeRateWithAmount(final String from, final String to, final BigDecimal amount) {

        final String url = getExchangeUrl(from, to, amount);

        return Optional.ofNullable(restTemplate.getForObject(url, ExchangeResponse.class))
                .orElseThrow(() -> new ExchangeNotFoundException("Exchange rate not found for " + from + " to " + to));

    }

    /**
     * Constructs the URL for the exchange rate API request.
     *
     * @param from The currency code to convert from.
     * @param to The currency code to convert to.
     * @param amount The amount of money to convert.
     * @return A {@link String} representing the URL for the exchange rate API request.
     */
    private String getExchangeUrl(final String from, final String to, final BigDecimal amount) {
        return String.format("%s/convert?access_key=%s&from=%s&to=%s&amount=%s", EXCHANGE_API_BASE_URL, EXCHANGE_API_API_KEY, from, to, amount);
    }

}
