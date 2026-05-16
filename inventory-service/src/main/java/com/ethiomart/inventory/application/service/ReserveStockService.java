package com.ethiomart.inventory.application.service;

import com.ethiomart.events.StockFailedEvent;
import com.ethiomart.events.StockReservedEvent;
import com.ethiomart.inventory.application.port.in.ReserveStockCommand;
import com.ethiomart.inventory.application.port.in.ReserveStockUseCase;
import com.ethiomart.inventory.application.port.out.EventPublisherPort;
import com.ethiomart.inventory.domain.model.Product;
import com.ethiomart.inventory.domain.repository.ProductRepository;

import java.time.Instant;
import java.util.UUID;

public class ReserveStockService implements ReserveStockUseCase {

    private final ProductRepository productRepository;
    private final EventPublisherPort eventPublisher;

    public ReserveStockService(ProductRepository productRepository, EventPublisherPort eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void reserve(ReserveStockCommand command) {
        Product product = productRepository.findById(command.productId()).orElse(null);
        if (product == null || !product.reserve(command.quantity())) {
            eventPublisher.publishStockFailed(new StockFailedEvent(
                    UUID.randomUUID().toString(),
                    command.orderId(),
                    command.productId(),
                    "Insufficient stock",
                    Instant.now()));
            if (product != null) {
                productRepository.save(product);
            }
            return;
        }

        productRepository.save(product);
        eventPublisher.publishStockReserved(new StockReservedEvent(
                UUID.randomUUID().toString(),
                command.orderId(),
                command.productId(),
                command.quantity(),
                Instant.now()));
    }
}
