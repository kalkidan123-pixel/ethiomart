package com.ethiomart.payment.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {

    public enum Status { COMPLETED, FAILED }

    private final String id;
    private final String orderId;
    private final BigDecimal amount;
    private final Status status;

    public Payment(String orderId, BigDecimal amount, Status status) {
        this(UUID.randomUUID().toString(), orderId, amount, status);
    }

    public Payment(String id, String orderId, BigDecimal amount, Status status) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public Status getStatus() { return status; }
}
