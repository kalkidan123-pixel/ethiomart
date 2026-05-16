package com.ethiomart.shipping.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_readiness")
public class OrderReadinessEntity {

    @Id
    private String orderId;

    @Column(nullable = false)
    private boolean paymentCompleted;

    @Column(nullable = false)
    private boolean stockReserved;

    @Column(nullable = false)
    private boolean shipmentCreated;

    protected OrderReadinessEntity() {
    }

    public OrderReadinessEntity(String orderId, boolean paymentCompleted, boolean stockReserved, boolean shipmentCreated) {
        this.orderId = orderId;
        this.paymentCompleted = paymentCompleted;
        this.stockReserved = stockReserved;
        this.shipmentCreated = shipmentCreated;
    }

    public String getOrderId() { return orderId; }
    public boolean isPaymentCompleted() { return paymentCompleted; }
    public void setPaymentCompleted(boolean paymentCompleted) { this.paymentCompleted = paymentCompleted; }
    public boolean isStockReserved() { return stockReserved; }
    public void setStockReserved(boolean stockReserved) { this.stockReserved = stockReserved; }
    public boolean isShipmentCreated() { return shipmentCreated; }
    public void setShipmentCreated(boolean shipmentCreated) { this.shipmentCreated = shipmentCreated; }
}
