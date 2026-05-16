package com.ethiomart.inventory.domain.model;

public class Product {

    private final String id;
    private int stockQuantity;

    public Product(String id, int stockQuantity) {
        this.id = id;
        this.stockQuantity = stockQuantity;
    }

    public String getId() {
        return id;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public boolean reserve(int quantity) {
        if (quantity <= 0 || stockQuantity < quantity) {
            return false;
        }
        stockQuantity -= quantity;
        return true;
    }
}
