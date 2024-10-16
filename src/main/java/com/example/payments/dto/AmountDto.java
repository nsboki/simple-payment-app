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
@Schema(description = "Data Transfer Object for Amount")
public class AmountDto {

    @Schema(description = "Currency code of the payment", example = "RSD")
    private String currency;

    @Schema(description = "Value of the payment", example = "1.23")
    private BigDecimal value;

}
