package com.example.demo.service;

import com.example.demo.model.ExchangeRate;
import com.example.demo.model.dto.request.ExchangeRateRequest;

public interface ExchangeRateService {

    ExchangeRate exchangeRate(final ExchangeRateRequest exchangeRateRequest);

}
