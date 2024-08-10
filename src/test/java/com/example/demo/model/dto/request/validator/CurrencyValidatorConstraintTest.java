package com.example.demo.model.dto.request.validator;

import com.example.demo.model.dto.request.ConvertRequest;
import com.example.demo.model.dto.request.ExchangeRateRequest;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testIsValid_NullValue() {
        // Act
        boolean result = validator.isValid(null, context);

        // Assert
        assertTrue(result, "Expected null value to be valid");
    }

    @Test
    public void testIsValid_ValidConvertRequest() {
        // Arrange
        ConvertRequest request = ConvertRequest.builder()
                .from("USD")
                .to("EUR")
                .build();

        // Act
        boolean result = validator.isValid(request, context);

        // Assert
        assertTrue(result, "Expected valid currencies to be valid");
    }

    @Test
    public void testIsValid_InvalidConvertRequest() {

        // Arrange
        ConvertRequest request = ConvertRequest.builder()
                .from("INVALID")
                .to("EUR")
                .build();

        // Act
        boolean result = validator.isValid(request, context);

        // Assert
        assertFalse(result, "Expected invalid currency to be invalid");
    }

    @Test
    public void testIsValid_InvalidExchangeRateRequest() {

        // Arrange
        ExchangeRateRequest request = ExchangeRateRequest.builder()
                .from("USD")
                .to("INVALID")
                .build();

        // Act
        boolean result = validator.isValid(request, context);

        // Assert
        assertFalse(result, "Expected invalid currency to be invalid");

    }

    @Test
    public void testIsValid_OtherType() {
        // Arrange
        Object otherObject = new Object();

        // Act
        boolean result = validator.isValid(otherObject, context);

        // Assert
        assertFalse(result, "Expected other types to be invalid");
    }

}