package com.ethiomart.inventory.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private int stockQuantity;

    protected ProductEntity() {
    }

    public ProductEntity(String id, int stockQuantity) {
        this.id = id;
        this.stockQuantity = stockQuantity;
    }

    public String getId() { return id; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
