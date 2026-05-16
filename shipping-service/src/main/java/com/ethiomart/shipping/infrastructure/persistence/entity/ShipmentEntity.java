package com.ethiomart.shipping.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private String trackingNumber;

    protected ShipmentEntity() {
    }

    public ShipmentEntity(String id, String orderId, String trackingNumber) {
        this.id = id;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
    }

    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public String getTrackingNumber() { return trackingNumber; }
}
