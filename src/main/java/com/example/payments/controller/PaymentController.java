package com.example.payments.controller;

import com.example.payments.dto.PaymentDto;
import com.example.payments.services.impl.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payments")
@Tag(name = "Payment API", description = "API for Payment CRUD operations")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping
    @Operation(summary = "Create a new payment", description = "Add a new payment to the system")
    public ResponseEntity<PaymentDto> createPayment(
            @Parameter(description = "Payment object to be created")
            @RequestBody PaymentDto paymentDto) {
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
