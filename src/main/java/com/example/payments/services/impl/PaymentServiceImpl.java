package com.example.payments.services.impl;

import com.example.payments.dto.PaymentDto;
import com.example.payments.entity.Payment;
import com.example.payments.mapper.PaymentMapper;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.mapToPayment(paymentDto);
        Payment createdPayment = paymentRepository.save(payment);
        return PaymentMapper.mapToPaymentDto(createdPayment);
    }

    @Override
    public PaymentDto getPaymentById(Long paymentId) {
        return null;
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return null;
    }

    @Override
    public PaymentDto updatePaymentById(Long paymentId, PaymentDto updatedPayment) {
        return null;
    }

    @Override
    public void deletePaymentById(Long paymentId) {

    }
}
