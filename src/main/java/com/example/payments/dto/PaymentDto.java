package com.example.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Payment")
public class PaymentDto {

    @Schema(description = "Unique identifier of the payment", example = "1")
    private Long id;

    @Schema(description = "Currency code and value of the payment")
    private AmountDto amount;

    @Schema(description = "Payment note", example = "pays, receives")
    private String note;
}
