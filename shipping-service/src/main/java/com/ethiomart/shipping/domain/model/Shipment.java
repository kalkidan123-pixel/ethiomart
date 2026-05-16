package com.ethiomart.shipping.domain.model;

import java.util.UUID;

public class Shipment {

    private final String id;
    private final String orderId;
    private final String trackingNumber;

    public Shipment(String orderId) {
        this(UUID.randomUUID().toString(), orderId, "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }

    public Shipment(String id, String orderId, String trackingNumber) {
        this.id = id;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
    }

    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public String getTrackingNumber() { return trackingNumber; }
}
