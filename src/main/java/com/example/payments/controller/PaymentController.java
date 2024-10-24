package com.example.payments.controller;

import com.example.payments.dto.PaymentDto;
import com.example.payments.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payments")
@Tag(name = "Payment API", description = "API for Payment CRUD operations")
@Validated
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Create a new payment", description = "Add a new payment to the system")
    public ResponseEntity<PaymentDto> createPayment(@Parameter(description = "Payment object to be created") @Valid @RequestBody PaymentDto paymentDto) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a payment", description = "Get a payment with a given id from the system")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable("id") Long paymentId) {
        PaymentDto paymentById = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentById);
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Get all payments from the system")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> allPayments = paymentService.getAllPayments();
        return ResponseEntity.ok(allPayments);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a payment", description = "Update a payment with a given id if exists")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable("id") Long paymentId,
                                                    @RequestBody PaymentDto updatedPayment) {
        PaymentDto paymentDto = paymentService.updatePaymentById(paymentId, updatedPayment);
        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a payment", description = "Delete a payment with a given id if exists")
    public ResponseEntity<String> deletePayment(@PathVariable("id") Long paymentId) {
        paymentService.deletePaymentById(paymentId);
        return ResponseEntity.ok("Payment deleted successfully!");
    }

}
