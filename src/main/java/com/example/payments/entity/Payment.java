package com.example.payments.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAYMENTS")
@Schema(description = "Payment entity")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the payment", example = "1")
    private Long id;

    @Schema(description = "Currency code of the payment", example = "RSD")
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Schema(description = "Value of the payment", example = "1.23")
    @Column(name = "AMOUNT_VALUE")
    private BigDecimal amountValue;

    @Schema(description = "Payment note", example = "pays, receives")
    @Column(name = "NOTE")
    private String note;

}
