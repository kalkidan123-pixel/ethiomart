package com.ethiomart.order.application.service;

import com.ethiomart.events.OrderCreatedEvent;
import com.ethiomart.order.application.port.in.CreateOrderCommand;
import com.ethiomart.order.application.port.in.CreateOrderUseCase;
import com.ethiomart.order.application.port.in.OrderResult;
import com.ethiomart.order.application.port.out.EventPublisherPort;
import com.ethiomart.order.domain.model.Order;
import com.ethiomart.order.domain.repository.OrderRepository;
import com.ethiomart.order.domain.service.PricingService;

import java.time.Instant;
import java.util.UUID;

public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final PricingService pricingService;
    private final EventPublisherPort eventPublisher;

    public CreateOrderService(
            OrderRepository orderRepository,
            PricingService pricingService,
            EventPublisherPort eventPublisher) {
        this.orderRepository = orderRepository;
        this.pricingService = pricingService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderResult create(CreateOrderCommand command) {
        var amount = pricingService.calculateTotal(command.productId(), command.quantity());
        Order order = new Order(command.userId(), command.productId(), command.quantity(), amount);
        Order saved = orderRepository.save(order);

        eventPublisher.publishOrderCreated(new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                saved.getId(),
                saved.getUserId(),
                saved.getProductId(),
                saved.getQuantity(),
                saved.getAmount(),
                Instant.now()));

        return new OrderResult(
                saved.getId(),
                saved.getUserId(),
                saved.getProductId(),
                saved.getQuantity(),
                saved.getAmount());
    }
}
