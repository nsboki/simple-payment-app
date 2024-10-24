package com.example.payments.services.impl;

import com.example.payments.base.IntegrationTestBase;
import com.example.payments.dto.PaymentDto;
import com.example.payments.services.PaymentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest extends IntegrationTestBase {

    @Autowired
    private PaymentService paymentService;

    //TODO: Implement integraton tests
    @Test
    void createPayment() {
    }

    @Test
    void getPaymentById() {
        // given
        Long id = 1L;

        // when
        PaymentDto payment = paymentService.getPaymentById(id);

        // then
        assertThat(payment).isNotNull();
    }

    @Test
    void getAllPayments() {
    }

    @Test
    void updatePaymentById() {
    }

    @Test
    void deletePaymentById() {
    }
}