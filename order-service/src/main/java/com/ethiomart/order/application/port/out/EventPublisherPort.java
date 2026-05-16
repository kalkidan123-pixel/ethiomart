package com.ethiomart.order.application.port.out;

import com.ethiomart.events.OrderCreatedEvent;

public interface EventPublisherPort {

    void publishOrderCreated(OrderCreatedEvent event);
}
