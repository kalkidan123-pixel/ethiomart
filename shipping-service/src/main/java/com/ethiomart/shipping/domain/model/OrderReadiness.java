package com.ethiomart.shipping.domain.model;

public class OrderReadiness {

    private final String orderId;
    private boolean paymentCompleted;
    private boolean stockReserved;
    private boolean shipmentCreated;

    public OrderReadiness(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public boolean isStockReserved() {
        return stockReserved;
    }

    public boolean isShipmentCreated() {
        return shipmentCreated;
    }

    public void markPaymentCompleted() {
        this.paymentCompleted = true;
    }

    public void markStockReserved() {
        this.stockReserved = true;
    }

    public boolean isReadyForShipment() {
        return paymentCompleted && stockReserved && !shipmentCreated;
    }

    public void markShipmentCreated() {
        this.shipmentCreated = true;
    }
}
