package com.example.payments.services.impl;

import com.example.payments.dto.PaymentDto;
import com.example.payments.entity.Payment;
import com.example.payments.exception.ResourceNotFoundException;
import com.example.payments.mapper.PaymentMapper;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("The payment with the given id (" + paymentId + ") does not exists.")
                );
        return PaymentMapper.mapToPaymentDto(payment);
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(PaymentMapper::mapToPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto updatePaymentById(Long paymentId, PaymentDto updatedPayment) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("The payment with the given id (" + paymentId + ") does not exists.")
                );
        payment.setId(paymentId);
        payment.setAmountValue(updatedPayment.getAmount().getValue());
        payment.setCurrencyCode(updatedPayment.getAmount().getCurrency());
        payment.setNote(updatedPayment.getNote());
        Payment updatedPaymentObj = paymentRepository.save(payment);
        return PaymentMapper.mapToPaymentDto(updatedPaymentObj);
    }

    @Override
    public void deletePaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("The payment with the given id (" + paymentId + ") does not exists")
                );
        paymentRepository.deleteById(paymentId);
    }
}
