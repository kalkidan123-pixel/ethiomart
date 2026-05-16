package com.ethiomart.order.domain.service;

import java.math.BigDecimal;
import java.util.Map;

public class PricingService {

    private static final Map<String, BigDecimal> UNIT_PRICES = Map.of(
            "PROD-001", new BigDecimal("100.00"),
            "PROD-002", new BigDecimal("250.00"));

    public BigDecimal calculateTotal(String productId, int quantity) {
        BigDecimal unitPrice = UNIT_PRICES.get(productId);
        if (unitPrice == null) {
            throw new IllegalArgumentException("Unknown product: " + productId);
        }
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
