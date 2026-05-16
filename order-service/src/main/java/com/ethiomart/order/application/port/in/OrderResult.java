package com.ethiomart.order.application.port.in;

import java.math.BigDecimal;

public record OrderResult(String orderId, String userId, String productId, int quantity, BigDecimal amount) {
}
