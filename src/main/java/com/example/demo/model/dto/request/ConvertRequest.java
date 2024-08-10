package com.example.demo.model.dto.request;


import com.example.demo.model.dto.request.validator.CurrencyValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents a request to convert an amount from one currency to another.
 * This class encapsulates the details required for a currency conversion request,
 * including the source currency, target currency, and the amount to be converted.
 * It includes validation annotations to ensure that the currency codes and amount
 * meet specific criteria.
 */
@Getter
@Setter
@Builder
@CurrencyValidator
public class ConvertRequest {

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter uppercase code")
    private String from;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a 3-letter uppercase code")
    private String to;

    @NotNull
    @Positive
    private BigDecimal amount;
}
