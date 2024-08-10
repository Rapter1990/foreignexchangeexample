package com.example.demo.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents the response containing details of a currency conversion history.
 * This class encapsulates the information about a specific currency conversion transaction,
 * including the transaction ID, amount, source and target currencies, converted amount,
 * and the timestamp of when the transaction was created.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertHistoryResponse {
    private String transactionId;
    private BigDecimal amount;
    private String from;
    private String to;
    private BigDecimal convertedAmount;
    private LocalDateTime createdAt;
}
