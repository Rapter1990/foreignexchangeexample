package com.example.demo.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertResponse {

    private String transactionId;
    private BigDecimal convertedAmount;

}
