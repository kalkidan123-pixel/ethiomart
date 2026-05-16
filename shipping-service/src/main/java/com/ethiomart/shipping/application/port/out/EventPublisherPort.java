package com.ethiomart.shipping.application.port.out;

import com.ethiomart.events.ShipmentCreatedEvent;

public interface EventPublisherPort {

    void publishShipmentCreated(ShipmentCreatedEvent event);
}
