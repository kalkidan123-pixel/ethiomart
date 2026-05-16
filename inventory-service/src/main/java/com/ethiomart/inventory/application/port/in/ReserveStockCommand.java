package com.ethiomart.inventory.application.port.in;

public record ReserveStockCommand(String orderId, String productId, int quantity) {
}
