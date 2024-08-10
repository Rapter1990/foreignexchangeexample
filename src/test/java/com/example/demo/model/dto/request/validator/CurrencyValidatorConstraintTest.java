package com.example.demo.model.dto.request.validator;

import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test class for {@link CurrencyValidatorConstraint}.
 * This class contains test cases to validate the behavior of the
 * {@link CurrencyValidatorConstraint} class, specifically the
 * {@link CurrencyValidatorConstraint#isValid(Object, ConstraintValidatorContext)} method.
 */
class CurrencyValidatorConstraintTest {

    @InjectMocks
    private CurrencyValidatorConstraint validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given Null Value - When Validate - Then Return True")
    void givenNullValue_whenValidate_thenReturnTrue() {

        // Given , When & Then
        boolean result = validator.isValid(null, context);

        assertTrue(result, "Expected null value to be valid");

    }

    @Test
    @DisplayName("Given Valid ConvertRequest - When Validate - Then Return True")
    void givenValidConvertRequest_whenValidate_thenReturnTrue() {

        // Given
        ConvertRequest request = ConvertRequest.builder()
                .from("USD")
                .to("EUR")
                .build();

        // When & Then
        boolean result = validator.isValid(request, context);

        assertTrue(result, "Expected valid currencies to be valid");

    }

    @Test
    @DisplayName("Given Invalid ConvertRequest - When Validate - Then Return False")
    void givenInvalidConvertRequest_whenValidate_thenReturnFalse() {

        // Given
        ConvertRequest request = ConvertRequest.builder()
                .from("INVALID")
                .to("EUR")
                .build();

        // When & Then
        boolean result = validator.isValid(request, context);

        assertFalse(result, "Expected invalid currency to be invalid");

    }

    @Test
    @DisplayName("Given Invalid ExchangeRateRequest - When Validate - Then Return False")
    void givenInvalidExchangeRateRequest_whenValidate_thenReturnFalse() {

        // Given
        ExchangeRateRequest request = ExchangeRateRequest.builder()
                .from("USD")
                .to("INVALID")
                .build();

        // When & Then
        boolean result = validator.isValid(request, context);

        assertFalse(result, "Expected invalid currency to be invalid");

    }

    @Test
    @DisplayName("Given Object of Other Type - When Validate - Then Return False")
    void givenObjectOfOtherType_whenValidate_thenReturnFalse() {

        // Given
        Object otherObject = new Object();

        // When & Then
        boolean result = validator.isValid(otherObject, context);

        assertFalse(result, "Expected other types to be invalid");

    }

}