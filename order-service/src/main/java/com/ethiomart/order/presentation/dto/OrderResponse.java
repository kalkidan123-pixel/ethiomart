package com.ethiomart.order.presentation.dto;

import java.math.BigDecimal;

public record OrderResponse(
        String orderId,
        String userId,
        String productId,
        int quantity,
        BigDecimal amount
) {
}
