package com.example.demo.model.dto.request.validator;

import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import com.example.demo.model.enums.EnumCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/**
 * Validator for the {@link CurrencyValidator} annotation.
 * This class implements the validation logic for currency codes. It checks whether
 * the provided currency codes in {@link ConvertRequest} or {@link ExchangeRateRequest}
 * are valid based on a predefined list of acceptable currency codes.
 */
public class CurrencyValidatorConstraint implements ConstraintValidator<CurrencyValidator, Object> {

    private static final List<String> VALID_CURRENCIES = Arrays.stream(EnumCurrency.values())
            .map(EnumCurrency::name)
            .toList();

    /**
     * Validates whether the given value is a valid currency code.
     * This method checks if the value is an instance of {@link ConvertRequest} or
     * {@link ExchangeRateRequest} and validates the currency codes provided in these
     * requests. If the value is neither of these types, the method returns {@code false}.
     *
     * @param value the object to validate, which can be of type {@link ConvertRequest}
     *              or {@link ExchangeRateRequest}
     * @param context the context in which the constraint is evaluated
     * @return {@code true} if the currency codes are valid; {@code false} otherwise
     */
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

    /**
     * Checks if the given currency code is in the list of valid currencies.
     * This method verifies if the provided currency code is present in the predefined
     * list of acceptable currency codes.
     *
     * @param currency the currency code to check
     * @return {@code true} if the currency code is valid; {@code false} otherwise
     */
    private boolean isValidCurrency(String currency) {
        return VALID_CURRENCIES.contains(currency);
    }

}
