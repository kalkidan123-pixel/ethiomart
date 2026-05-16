package com.ethiomart.payment.presentation.dto;

import java.math.BigDecimal;

public record PaymentResponse(String paymentId, String orderId, BigDecimal amount, String status) {
}
