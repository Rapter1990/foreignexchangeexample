package com.example.demo.service.impl;

import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import com.example.demo.model.dto.response.ExchangeResponse;
import com.example.demo.model.mapper.ExchangeResponseToExchangeRateMapper;
import com.example.demo.service.ExchangeRateService;
import com.example.demo.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeService exchangeService;

    private static final Integer UNIT_VALUE = 1;

    private final ExchangeResponseToExchangeRateMapper exchangeResponseToExchangeRateMapper =
            ExchangeResponseToExchangeRateMapper.initialize();

    @Override
    public ExchangeRate exchangeRate(ExchangeRateRequest exchangeRateRequest) {

        ExchangeResponse exchangeResponse = exchangeService.getExchangeRateWithAmount(
                exchangeRateRequest.getFrom(),exchangeRateRequest.getTo(), BigDecimal.valueOf(UNIT_VALUE)
        );

        return exchangeResponseToExchangeRateMapper.map(exchangeResponse);

    }

}
