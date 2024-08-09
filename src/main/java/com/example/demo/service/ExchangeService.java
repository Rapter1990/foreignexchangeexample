package com.example.demo.service;

import com.example.demo.model.dto.response.ExchangeResponse;

import java.math.BigDecimal;

public interface ExchangeService {

    ExchangeResponse getExchangeRateWithAmount(final String from, final String to, final BigDecimal amount);
}
