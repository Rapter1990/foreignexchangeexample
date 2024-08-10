package com.example.demo.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents the response containing details of a currency conversion.
 * This class encapsulates the results of a currency conversion operation,
 * including the transaction ID and the converted amount.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertResponse {

    private String transactionId;
    private BigDecimal convertedAmount;

}
