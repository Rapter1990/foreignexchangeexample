package com.example.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Represents a user domain object as {@link Convert} in the application.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Convert extends BaseDomainModel {

    private String transactionId;
    private BigDecimal amount;
    private String from;
    private String to;
    private BigDecimal convertedAmount;

}
