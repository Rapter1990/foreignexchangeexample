package com.example.demo.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
