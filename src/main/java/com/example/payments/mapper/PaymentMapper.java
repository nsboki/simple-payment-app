package com.example.payments.mapper;

import com.example.payments.dto.AmountDto;
import com.example.payments.dto.PaymentDto;
import com.example.payments.entity.Payment;

public class PaymentMapper {

    public static PaymentDto mapToPaymentDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                new AmountDto(payment.getCurrencyCode(),
                payment.getAmountValue()),
                payment.getNote()
        );
    }

    public static Payment mapToPayment(PaymentDto paymentDto) {
        return new Payment(
                paymentDto.getId(),
                paymentDto.getAmount().getCurrency(),
                paymentDto.getAmount().getValue(),
                paymentDto.getNote()
        );
    }
}
