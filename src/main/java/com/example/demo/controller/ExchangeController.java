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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller named {@link ExchangeController} for handling exchange rate and currency conversion operations.
 * This controller provides endpoints for converting currencies, retrieving exchange rates,
 * and getting conversion history. It also manages cache eviction periodically.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ExchangeController {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    private final CurrencyConversionService currencyConversionService;

    private final ExchangeRateService exchangeRateService;

    private final ConversionHistoryService conversionHistoryService;

    private final ConvertToConvertResponseMapper convertToConvertResponseMapper =
            ConvertToConvertResponseMapper.initialize();

    private final ExchangeRateToExchangeRateResponseMapper exchangeRateToExchangeRateResponseMapper =
            ExchangeRateToExchangeRateResponseMapper.initialize();

    private final ConvertToConvertHistoryResponseMapper convertToConvertHistoryResponseMapper =
            ConvertToConvertHistoryResponseMapper.initialize();

    /**
     * Converts currency based on the provided ExchangeRateRequest.
     *
     * @param request the request containing currency conversion details.
     * @return a ResponseEntity containing the ExchangeRateResponse with the conversion result.
     */
    @RateLimiter(name = "basic")
    @PostMapping("/exchange-rate")
    @Operation(
            summary = "Convert Currency",
            description = "Converts currency based on the provided ExchangeRateRequest.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful conversion",
                                 content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = ExchangeRateResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request format")
            }
    )
    public ResponseEntity<ExchangeRateResponse> convertCurrency(@Valid @RequestBody final ExchangeRateRequest request) {
        ExchangeRate exchangeRate = exchangeRateService.exchangeRate(request);
        return ResponseEntity.ok(exchangeRateToExchangeRateResponseMapper.map(exchangeRate));
    }

    /**
     * Converts currency based on the provided ConvertRequest.
     *
     * @param request the request containing conversion details.
     * @return a ResponseEntity containing the ConvertResponse with the conversion result.
     */
    @RateLimiter(name = "basic")
    @PostMapping("/convert")
    @Operation(
            summary = "Convert Currency",
            description = "Converts currency based on the provided ConvertRequest.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful conversion",
                            content = @Content(mediaType = "application/json",
                                               schema = @Schema(implementation = ConvertResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request format")
            }
    )
    public ResponseEntity<ConvertResponse> exchangeRate(@Valid @RequestBody final ConvertRequest request) {
        Convert convertDto = currencyConversionService.convertCurrency(request);
        return ResponseEntity.ok(convertToConvertResponseMapper.map(convertDto));
    }

    /**
     * Retrieves the conversion history based on the provided ConversionHistoryFilterRequest.
     *
     * @param request the request containing filter criteria for retrieving conversion history.
     * @return a ResponseEntity containing a paginated list of ConvertHistoryResponse.
     */
    @RateLimiter(name = "basic")
    @PostMapping("/history")
    @Operation(
            summary = "Get Conversion History",
            description = "Retrieves the conversion history based on the provided ConversionHistoryFilterRequest.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of conversion history",
                                 content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = CustomPage.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request format")
            }
    )
    public ResponseEntity<CustomPage<ConvertHistoryResponse>> getConversionHistory (
            @RequestBody ConversionHistoryFilterRequest request) {

        Page<Convert> corvertPage = conversionHistoryService.getConversionHistory(request);

        List<ConvertHistoryResponse> responseList = corvertPage.getContent().stream()
                .map(convertToConvertHistoryResponseMapper::map)
                .toList();

        CustomPage<ConvertHistoryResponse> customPage = CustomPage.of(responseList, corvertPage);
        return ResponseEntity.ok(customPage);
    }

    /**
     * Clears the cache entries periodically.
     * This method is scheduled to run at a fixed rate and clears all cache entries.
     */
    @CacheEvict(allEntries = true, cacheNames = {"exchanges"})
    @PostConstruct
    @Scheduled(fixedRateString = "${exchange-api.cache-ttl}")
    public void clearCache() {
        logger.info("Caches are cleared");
    }

}
