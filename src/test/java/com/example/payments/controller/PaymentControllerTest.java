package com.example.payments.controller;

import com.example.payments.dto.AmountDto;
import com.example.payments.dto.PaymentDto;
import com.example.payments.exception.ResourceNotFoundException;
import com.example.payments.services.impl.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentServiceImpl paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentDto invalidPayment;

    private PaymentDto validPayment;

    @BeforeEach
    public void setup() {
        validPayment = new PaymentDto( 1L, new AmountDto("RSD", new BigDecimal("1.23")), "Payment note");
        invalidPayment = new PaymentDto(10L, new AmountDto("RS", null), null);

    }

    @Test
    public void testGetPayment_Success() throws Exception {
        // GIVEN
        PaymentDto paymentDto = validPayment;
        // When paymentService.getPaymentById(1L) is called, return paymentDto
        when(paymentService.getPaymentById(1L)).thenReturn(paymentDto);

        // WHEN - perform the Get request
        ResultActions resultActions = mockMvc.perform(get("/api/payments/1")
                .contentType(MediaType.APPLICATION_JSON));

        // THEN - Check the results
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))  // Assert that the ID in the response is 1
                .andExpect(jsonPath("$.amount.currency", is("RSD")))  // Assert amount currency
                .andExpect(jsonPath("$.amount.value", is(1.23)))  // Assert amount value
                .andExpect(jsonPath("$.note", is("Payment note"))); // Assert note

    }

    @Test
    public void testGetPayment_NotFound() throws Exception {

        // GIVEN

        // Simulate service throwing PaymentNotFoundException
        when(paymentService.getPaymentById(1L)).thenThrow(new ResourceNotFoundException("The payment with the given id does not exists."));

        // WHEN - Perform GET request
        mockMvc.perform(get("/api/payments/1")
                        .contentType(MediaType.APPLICATION_JSON))

        // THEN - Check the results
                .andExpect(status().isNotFound());  // Assert HTTP 404 status
    }

    @Test
    public void testCreatePayment_Success() throws Exception {

        // GIVEN - Input PaymentDto and the expected response PaymentDto
        PaymentDto inputPaymentDto = validPayment;
        PaymentDto savedPaymentDto = validPayment;

        // Mock the service layer to return the expected saved PaymentDto
        when(paymentService.createPayment(any())).thenReturn(savedPaymentDto);

        // WHEN - Perform POST request
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPaymentDto)))  // Convert inputPaymentDto to JSON

                // THEN - Check the response
                .andExpect(status().isCreated()) // Expect HTTP 201 Created
                .andExpect(jsonPath("$.id", is(1)))  // Assert that the ID in the response is 1
                .andExpect(jsonPath("$.amount.currency", is("RSD")))  // Assert amount currency
                .andExpect(jsonPath("$.amount.value", is(1.23)))  // Assert amount value
                .andExpect(jsonPath("$.note", is("Payment note")));

    }

    @Test
    public void testCreatePaymentReturnValidationErrors_whenInvalidPayment() throws Exception {

        // GIVEN - Input invalid PaymentDto
        PaymentDto inputPaymentDto = invalidPayment;

        // WHEN - Perform POST request
        ResultActions resultActions = mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputPaymentDto)));// Convert inputPaymentDto to JSON

        // THEN - Check the response
        resultActions.andExpect(status().isBadRequest()) // Expect HTTP 400 Bad Request
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Check that validation errors are returned in the respons;
                // TODO Problem with amount assert should be fixed.
//                .andExpect(jsonPath("$.amount.currency").value("Payment currency mast have exactly 3 characters"))
//                .andExpect(jsonPath("$.amount.value").value("Payment amount cannot be null"))
                .andExpect(jsonPath("$.note").value("Payment note cannot be null"));

    }

    @Test
    public void testGetAllPayments() throws Exception {
        // GIVEN - Expected list of payments
        List<PaymentDto> payments = Arrays.asList(validPayment, validPayment);

        // Mock the service layer to perform GET for all payments and return the expected list of payments
        when(paymentService.getAllPayments()).thenReturn(payments);

        // WHEN - Perform GET all payments request
        mockMvc.perform(get("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON))

        // THEN - Check the response
                .andExpect(status().isOk()) // Check if status is 200 OK
                .andExpect(jsonPath("$", hasSize(2))) // Verify that the response contains 2 elements
                .andExpect(jsonPath("$[0].id", is(1))) // Check first payment's id
                .andExpect(jsonPath("$[0].amount.currency", is("RSD"))) // Check first payment's currency
                .andExpect(jsonPath("$[0].amount.value", is(1.23))) // Check first payment's amount
                .andExpect(jsonPath("$[0].note", is("Payment note"))) // Check first payment's note
                .andExpect(jsonPath("$[1].id", is(1))) // Check second payment's id
                .andExpect(jsonPath("$[1].amount.currency", is("RSD"))) // Check second payment's currency
                .andExpect(jsonPath("$[1].amount.value", is(1.23))) // Check second payment's amount
                .andExpect(jsonPath("$[1].note", is("Payment note"))); // Check second payment's note

    }

    @Test
    public void testUpdatePayment_Success() throws Exception {

        // GIVEN - Input payment dto and expected updated payment dto
        PaymentDto inputPayment = validPayment;
        PaymentDto updatedPayment = validPayment;

        // Mock the service layer to perform update payment action and return expected updated payment
        when(paymentService.updatePaymentById(any(), any())).thenReturn(updatedPayment);

        // wHEN - Perform PUT request for payment update
        mockMvc.perform(put("/api/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPayment)))

        // THEN - Check the response
                .andExpect(status().isOk()) // Check the status is 200 ok
                .andExpect(jsonPath("$.id", is(1))) // Check payment's id
                .andExpect(jsonPath("$.amount.currency", is("RSD"))) // Check payment's currency
                .andExpect(jsonPath("$.amount.value", is(1.23))) // Check payment's amount
                .andExpect(jsonPath("$.note", is("Payment note"))); // Check payment's note
    }

    @Test
    public void testDeletePayment_Success() throws Exception {

        // GIVEN - Payment id
        Long paymentId = 1L;

        // Mock the service layer to perform delete action and return expected response
        doNothing().when(paymentService).deletePaymentById(paymentId);

        // WHEN - perform DELETE request for delete payment
        mockMvc.perform(delete("/api/payments/{id}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON))

        // THEN - Check the response
                .andExpect(status().isOk()) // Check the status is 200 ok
                .andExpect(content().string("Payment deleted successfully!")); // Check the response content

        verify(paymentService).deletePaymentById(paymentId); // verify that the service method is called with the correct id

    }
}