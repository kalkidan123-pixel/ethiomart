package com.ethiomart.payment.application.port.in;

import java.math.BigDecimal;

public record ProcessPaymentCommand(String orderId, BigDecimal amount) {
}
