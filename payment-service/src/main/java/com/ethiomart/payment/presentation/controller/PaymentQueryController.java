package com.ethiomart.payment.presentation.controller;

import com.ethiomart.payment.domain.model.Payment;
import com.ethiomart.payment.domain.repository.PaymentRepository;
import com.ethiomart.payment.presentation.dto.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments")
public class PaymentQueryController {

    private final PaymentRepository paymentRepository;

    public PaymentQueryController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get payment by order id")
    public ResponseEntity<PaymentResponse> getByOrderId(@PathVariable String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus().name());
    }
}
