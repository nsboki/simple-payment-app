package com.example.payments.services;

import com.example.payments.dto.PaymentDto;

import java.util.List;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto paymentDto);

    PaymentDto getPaymentById(Long paymentId);

    List<PaymentDto> getAllPayments();

    PaymentDto updatePaymentById(Long paymentId, PaymentDto updatedPayment);

    void deletePaymentById(Long paymentId);

}
