package com.example.demo.controller;

import com.example.demo.base.AbstractRestControllerTest;
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
import com.example.demo.model.pagination.CustomPaging;
import com.example.demo.service.ConversionHistoryService;
import com.example.demo.service.CurrencyConversionService;
import com.example.demo.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ExchangeControllerTest extends AbstractRestControllerTest {

    @MockBean
    private CurrencyConversionService currencyConversionService;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @MockBean
    private ConversionHistoryService conversionHistoryService;

    private final ConvertToConvertResponseMapper convertToConvertResponseMapper =
            ConvertToConvertResponseMapper.initialize();

    private final ExchangeRateToExchangeRateResponseMapper exchangeRateToExchangeRateResponseMapper =
            ExchangeRateToExchangeRateResponseMapper.initialize();

    private final ConvertToConvertHistoryResponseMapper convertToConvertHistoryResponseMapper =
            ConvertToConvertHistoryResponseMapper.initialize();

    @Test
    public void testConvertCurrency() throws Exception {

        // Given
        ExchangeRateRequest request = ExchangeRateRequest.builder()
                .from("USD")
                .to("EUR")
                .build();

        ExchangeRate exchangeRate = ExchangeRate.builder()
                .result(new BigDecimal("0.85"))
                .build();

        ExchangeRateResponse exchangeRateResponse =
                exchangeRateToExchangeRateResponseMapper.map(exchangeRate);

        // When
        when(exchangeRateService.exchangeRate(any(ExchangeRateRequest.class)))
                .thenReturn(exchangeRate);


        // Then
        mockMvc.perform(post("/api/v1/exchange-rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(exchangeRateResponse.getResult()));

        // Verify
        verify(exchangeRateService).exchangeRate(any(ExchangeRateRequest.class));

    }

    @Test
    public void testExchangeRate() throws Exception {

        // Given
        ConvertRequest request = ConvertRequest.builder()
                .from("USD")
                .to("EUR")
                .amount(new BigDecimal("100.00"))
                .build();

        Convert convert = Convert.builder()
                .transactionId("12345")
                .amount(new BigDecimal("100.00"))
                .from("USD")
                .to("EUR")
                .convertedAmount(new BigDecimal("85.00"))
                .build();

        ConvertResponse convertResponse = convertToConvertResponseMapper.map(convert);

        DecimalFormat decimalFormat = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
        String actualConvertedAmount = decimalFormat.format(convertResponse.getConvertedAmount());

        // When
        when(currencyConversionService.convertCurrency(any(ConvertRequest.class)))
                .thenReturn(convert);


        // Then
        mockMvc.perform(post("/api/v1/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(convertResponse.getTransactionId()))
                .andExpect(jsonPath("$.convertedAmount").value(actualConvertedAmount));

        // Verify
        verify(currencyConversionService).convertCurrency(any(ConvertRequest.class));

    }

    @Test
    public void testGetConversionHistory() throws Exception {

        // Given
        ConversionHistoryFilterRequest conversionHistoryFilterRequest = ConversionHistoryFilterRequest.builder()
                .transactionId("tx123")
                .date(LocalDate.now())
                .pagination(new CustomPaging(1, 10))
                .build();

        Convert convert = Convert.builder()
                .transactionId("12345")
                .amount(new BigDecimal("100.00"))
                .from("USD")
                .to("EUR")
                .convertedAmount(new BigDecimal("85.00"))
                .build();

        Page<Convert> convertPage = new PageImpl<>(List.of(convert), PageRequest.of(0, 10), 1);

        List<ConvertHistoryResponse> responseList = convertPage.getContent().stream()
                .map(convertToConvertHistoryResponseMapper::map)
                .toList();


        CustomPage<ConvertHistoryResponse> customPage = CustomPage.of(responseList, convertPage);

        DecimalFormat decimalFormat = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
        String actualConvertedAmount = decimalFormat.format(customPage.getContent().get(0).getConvertedAmount());

        // When
        when(conversionHistoryService.getConversionHistory(any(ConversionHistoryFilterRequest.class)))
                .thenReturn(convertPage);

        // Then
        mockMvc.perform(post("/api/v1/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(conversionHistoryFilterRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].transactionId").value(customPage.getContent().get(0).getTransactionId()))
                .andExpect(jsonPath("$.content[0].convertedAmount").value(actualConvertedAmount))
                .andExpect(jsonPath("$.totalElementCount").value(customPage.getTotalElementCount()));

        // Verify
        verify(conversionHistoryService).getConversionHistory(any(ConversionHistoryFilterRequest.class));

    }

}