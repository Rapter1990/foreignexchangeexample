package com.example.demo.controller;

import com.example.demo.model.Convert;
import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import com.example.demo.model.dto.response.ConvertResponse;
import com.example.demo.model.dto.response.ExchangeRateResponse;
import com.example.demo.model.mapper.ConvertToConvertResponseMapper;
import com.example.demo.model.mapper.ExchangeRateToExchangeRateResponseMapper;
import com.example.demo.service.CurrencyConversionService;
import com.example.demo.service.ExchangeRateService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ExchangeController {

    private final CurrencyConversionService currencyConversionService;

    private final ExchangeRateService exchangeRateService;

    private final ConvertToConvertResponseMapper convertToConvertResponseMapper =
            ConvertToConvertResponseMapper.initialize();

    private final ExchangeRateToExchangeRateResponseMapper exchangeRateToExchangeRateResponseMapper =
            ExchangeRateToExchangeRateResponseMapper.initialize();

    @RateLimiter(name = "basic")
    @PostMapping("/exchange-rate")
    public ResponseEntity<ExchangeRateResponse> convertCurrency(@Valid @RequestBody final ExchangeRateRequest request) {
        ExchangeRate exchangeRate = exchangeRateService.exchangeRate(request);
        return ResponseEntity.ok(exchangeRateToExchangeRateResponseMapper.map(exchangeRate));
    }

    @RateLimiter(name = "basic")
    @PostMapping("/convert")
    public ResponseEntity<ConvertResponse> exchangeRate(@Valid @RequestBody final ConvertRequest request) {
        Convert convertDto = currencyConversionService.convertCurrency(request);
        return ResponseEntity.ok(convertToConvertResponseMapper.map(convertDto));
    }

}
