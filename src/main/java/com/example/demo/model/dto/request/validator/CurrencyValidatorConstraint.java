package com.example.demo.model.dto.request.validator;

import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import com.example.demo.model.enums.EnumCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CurrencyValidatorConstraint implements ConstraintValidator<CurrencyValidator, Object> {

    private static final List<String> VALID_CURRENCIES = Arrays.stream(EnumCurrency.values())
            .map(EnumCurrency::name)
            .toList();
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value instanceof ConvertRequest request) {
            return isValidCurrency(request.getFrom()) && isValidCurrency(request.getTo());
        } else if (value instanceof ExchangeRateRequest request) {
            return isValidCurrency(request.getFrom()) && isValidCurrency(request.getTo());
        }

        return false;
    }

    private boolean isValidCurrency(String currency) {
        return VALID_CURRENCIES.contains(currency);
    }
}
