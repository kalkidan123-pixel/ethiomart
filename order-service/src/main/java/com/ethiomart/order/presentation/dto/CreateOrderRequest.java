package com.ethiomart.order.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(
        @NotBlank String productId,
        @Min(1) int quantity
) {
}
