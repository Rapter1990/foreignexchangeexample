package com.example.demo.controller;

import com.example.demo.model.Convert;
import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.request.ConversionHistoryFilterRequest;
import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import com.example.demo.model.dto.response.ConvertHistoryResponse;
import com.example.demo.model.dto.response.ConvertResponse;
import com.example.demo.model.dto.response.ExchangeRateResponse;
import com.example.demo.model.mapper.ConvertToConvertHistoryResponseMapper;
import com.example.demo.model.mapper.ConvertToConvertResponseMapper;
import com.example.demo.model.mapper.ExchangeRateToExchangeRateResponseMapper;
import com.example.demo.model.pagination.CustomPage;
import com.example.demo.service.ConversionHistoryService;
import com.example.demo.service.CurrencyConversionService;
import com.example.demo.service.ExchangeRateService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ExchangeController {

    private final CurrencyConversionService currencyConversionService;

    private final ExchangeRateService exchangeRateService;

    private final ConversionHistoryService conversionHistoryService;

    private final ConvertToConvertResponseMapper convertToConvertResponseMapper =
            ConvertToConvertResponseMapper.initialize();

    private final ExchangeRateToExchangeRateResponseMapper exchangeRateToExchangeRateResponseMapper =
            ExchangeRateToExchangeRateResponseMapper.initialize();

    private final ConvertToConvertHistoryResponseMapper convertToConvertHistoryResponseMapper =
            ConvertToConvertHistoryResponseMapper.initialize();

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

    @RateLimiter(name = "basic")
    @PostMapping("/history")
    public ResponseEntity<CustomPage<ConvertHistoryResponse>> getConversionHistory (
            @RequestBody ConversionHistoryFilterRequest request) {

        Page<Convert> corvertPage = conversionHistoryService.getConversionHistory(request);

        List<ConvertHistoryResponse> responseList = corvertPage.getContent().stream()
                .map(convertToConvertHistoryResponseMapper::map)
                .toList();

        CustomPage<ConvertHistoryResponse> customPage = CustomPage.of(responseList, corvertPage);
        return ResponseEntity.ok(customPage);
    }

}
