package com.ethiomart.shipping.infrastructure.messaging;

import com.ethiomart.events.StockReservedEvent;
import com.ethiomart.shipping.application.port.in.HandleStockReservedUseCase;
import com.ethiomart.shipping.infrastructure.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockReservedListener {

    private static final Logger log = LoggerFactory.getLogger(StockReservedListener.class);
    private final HandleStockReservedUseCase useCase;

    public StockReservedListener(HandleStockReservedUseCase useCase) {
        this.useCase = useCase;
    }

    @RabbitListener(queues = RabbitMqConfig.STOCK_RESERVED_QUEUE)
    public void onStockReserved(StockReservedEvent event) {
        log.info("Received stock.reserved for order {}", event.orderId());
        useCase.onStockReserved(event.orderId());
    }
}
