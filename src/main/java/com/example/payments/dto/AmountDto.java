package com.example.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Payment currency cannot be null")
    @Size(min = 3, max = 3, message = "Payment currency mast have exactly 3 characters")
    private String currency;

    @Schema(description = "Value of the payment", example = "1.23")
    @NotNull(message = "Payment amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Payment amount must be greater than 0")
    private BigDecimal value;

}
