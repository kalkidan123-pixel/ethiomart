package com.ethiomart.inventory.infrastructure.messaging;

import com.ethiomart.events.EventRoutingKeys;
import com.ethiomart.events.StockFailedEvent;
import com.ethiomart.events.StockReservedEvent;
import com.ethiomart.inventory.application.port.out.EventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishStockReserved(StockReservedEvent event) {
        rabbitTemplate.convertAndSend(EventRoutingKeys.EXCHANGE, EventRoutingKeys.STOCK_RESERVED, event);
    }

    @Override
    public void publishStockFailed(StockFailedEvent event) {
        rabbitTemplate.convertAndSend(EventRoutingKeys.EXCHANGE, EventRoutingKeys.STOCK_FAILED, event);
    }
}
