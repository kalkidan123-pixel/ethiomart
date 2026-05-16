package com.ethiomart.order.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private final String id;
    private final String userId;
    private final String productId;
    private final int quantity;
    private final BigDecimal amount;

    public Order(String userId, String productId, int quantity, BigDecimal amount) {
        this(UUID.randomUUID().toString(), userId, productId, quantity, amount);
    }

    public Order(String id, String userId, String productId, int quantity, BigDecimal amount) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.productId = Objects.requireNonNull(productId);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
        this.amount = Objects.requireNonNull(amount);
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getAmount() { return amount; }
}
