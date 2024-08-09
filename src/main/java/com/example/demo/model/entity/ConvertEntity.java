package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Represents an entity as {@link ConvertEntity} for users.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "CONVERTS")
public class ConvertEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "FROM_CURRENCY")
    private String fromCurrency;

    @Column(name = "TO_CURRENCY")
    private String toCurrency;

    @Column(name = "CONVERTED_AMOUNT")
    private BigDecimal convertedAmount;

}
