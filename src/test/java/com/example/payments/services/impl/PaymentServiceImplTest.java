package com.example.payments.services.impl;

import com.example.payments.base.IntegrationTestBase;
import com.example.payments.dto.AmountDto;
import com.example.payments.dto.PaymentDto;
import com.example.payments.entity.Payment;
import com.example.payments.exception.ResourceNotFoundException;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.services.PaymentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest extends IntegrationTestBase {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    private PaymentDto testPaymentDto;

    @BeforeEach
    void setUp() {
        testPaymentDto = new PaymentDto( 10L, new AmountDto("RSD", new BigDecimal("1.23")), "Payment note");
    }
    @Test
    void testCreatePayment() {
        //given
        PaymentDto inputPayment = testPaymentDto;

        //when
        PaymentDto createdPayment = paymentService.createPayment(inputPayment);

        //then
        assertNotNull(createdPayment);
        assertEquals(inputPayment.getAmount().getValue(), createdPayment.getAmount().getValue());
        assertEquals(inputPayment.getAmount().getCurrency(), createdPayment.getAmount().getCurrency());
        assertEquals(inputPayment.getNote(), createdPayment.getNote());

    }

    @Test
    void testGetPaymentById() {
        // given
        PaymentDto createdPayment = paymentService.createPayment(testPaymentDto);

        // when
        PaymentDto paymentById = paymentService.getPaymentById(createdPayment.getId());

        // then
        assertThat(paymentById).isNotNull();
        assertEquals(createdPayment.getId(), paymentById.getId());

    }

    @Test
    void testGetAllPayments() {
        //given payment from flyway sql script

        //when
        List<PaymentDto> allPayments = paymentService.getAllPayments();

        //then
        assertFalse(allPayments.isEmpty());
        assertEquals(1, allPayments.size());

    }

    @Test
    void testUpdatePaymentById() {
        //given
        PaymentDto createdPayment = paymentService.createPayment(testPaymentDto);
        AmountDto newAmount = new AmountDto("EUR", new BigDecimal(100.0));
        PaymentDto updatedPaymentDto = new PaymentDto(createdPayment.getId(), newAmount, "Updated Payment");

        //when
        PaymentDto updatedPayment = paymentService.updatePaymentById(createdPayment.getId(), updatedPaymentDto);

        //then
        assertNotNull(updatedPayment);
        assertEquals(newAmount.getValue(), updatedPayment.getAmount().getValue());
        assertEquals(newAmount.getCurrency(), updatedPayment.getAmount().getCurrency());
        assertEquals(updatedPaymentDto.getNote(), updatedPayment.getNote());

    }

    @Test
    void testDeletePaymentById() {
        //given
        PaymentDto createdPayment = paymentService.createPayment(testPaymentDto);

        //when
        paymentService.deletePaymentById(createdPayment.getId());

        //then
        Optional<Payment> paymentById = paymentRepository.findById(createdPayment.getId());
        assertTrue(paymentById.isEmpty());

    }

    @Test
    void testGetPaymentById_NotFound() {
        // given empty payments table
        Long invalidPaymentId = 123L;

        // When
        // Then
        assertThrows(ResourceNotFoundException.class, () -> paymentService.getPaymentById(invalidPaymentId));

    }
}