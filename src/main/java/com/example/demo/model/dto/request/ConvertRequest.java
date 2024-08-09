package com.example.demo.model.dto.request;


import com.example.demo.model.dto.request.validator.CurrencyValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
