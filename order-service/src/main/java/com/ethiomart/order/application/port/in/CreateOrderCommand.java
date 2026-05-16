package com.ethiomart.order.application.port.in;

public record CreateOrderCommand(String userId, String productId, int quantity) {
}
