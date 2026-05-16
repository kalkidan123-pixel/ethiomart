package com.ethiomart.inventory.application.port.out;

import com.ethiomart.events.StockFailedEvent;
import com.ethiomart.events.StockReservedEvent;

public interface EventPublisherPort {

    void publishStockReserved(StockReservedEvent event);

    void publishStockFailed(StockFailedEvent event);
}
