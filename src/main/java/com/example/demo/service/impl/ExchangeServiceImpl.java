package com.example.demo.service.impl;

import com.example.demo.exception.ExchangeNotFoundException;
import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.utils.Constants.EXCHANGE_API_API_KEY;
import static com.example.demo.utils.Constants.EXCHANGE_API_BASE_URL;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "exchanges")
public class ExchangeServiceImpl implements ExchangeService {

    private final RestTemplate restTemplate;

    @Override
    public ExchangeResponse getExchangeRateWithAmount(final String from, final String to, final BigDecimal amount) {

        final String url = getExchangeUrl(from, to, amount);

        return Optional.ofNullable(restTemplate.getForObject(url, ExchangeResponse.class))
                .orElseThrow(() -> new ExchangeNotFoundException("Exchange rate not found for " + from + " to " + to));

    }

    private String getExchangeUrl(final String from, final String to, final BigDecimal amount) {
        return String.format("%s/convert?access_key=%s&from=%s&to=%s&amount=%s", EXCHANGE_API_BASE_URL, EXCHANGE_API_API_KEY, from, to, amount);
    }

}
