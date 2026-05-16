package com.ethiomart.inventory.infrastructure.messaging;

import com.ethiomart.events.OrderCreatedEvent;
import com.ethiomart.inventory.application.port.in.ReserveStockCommand;
import com.ethiomart.inventory.application.port.in.ReserveStockUseCase;
import com.ethiomart.inventory.infrastructure.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);
    private final ReserveStockUseCase reserveStockUseCase;

    public OrderCreatedListener(ReserveStockUseCase reserveStockUseCase) {
        this.reserveStockUseCase = reserveStockUseCase;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Received order.created for inventory order {}", event.orderId());
        reserveStockUseCase.reserve(new ReserveStockCommand(
                event.orderId(), event.productId(), event.quantity()));
    }
}
