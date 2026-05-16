package com.ethiomart.order.presentation.controller;

import com.ethiomart.order.application.port.in.CreateOrderCommand;
import com.ethiomart.order.application.port.in.CreateOrderUseCase;
import com.ethiomart.order.application.port.in.OrderResult;
import com.ethiomart.order.application.port.out.TokenValidatorPort;
import com.ethiomart.order.presentation.dto.CreateOrderRequest;
import com.ethiomart.order.presentation.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final TokenValidatorPort tokenValidator;

    public OrderController(CreateOrderUseCase createOrderUseCase, TokenValidatorPort tokenValidator) {
        this.createOrderUseCase = createOrderUseCase;
        this.tokenValidator = tokenValidator;
    }

    @PostMapping
    @Operation(summary = "Create order", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request,
            HttpServletRequest httpRequest) {
        String userId = tokenValidator.validateAndExtractUserId(httpRequest.getHeader("Authorization"));
        OrderResult result = createOrderUseCase.create(
                new CreateOrderCommand(userId, request.productId(), request.quantity()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(
                result.orderId(),
                result.userId(),
                result.productId(),
                result.quantity(),
                result.amount()));
    }
}
