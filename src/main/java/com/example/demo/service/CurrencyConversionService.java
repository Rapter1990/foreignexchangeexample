package com.example.demo.service;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConvertRequest;

public interface CurrencyConversionService {

    Convert convertCurrency(final ConvertRequest convertRequest);

}
