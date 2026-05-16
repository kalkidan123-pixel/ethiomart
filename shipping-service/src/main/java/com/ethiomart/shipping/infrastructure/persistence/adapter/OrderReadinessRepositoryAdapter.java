package com.ethiomart.shipping.infrastructure.persistence.adapter;

import com.ethiomart.shipping.domain.model.OrderReadiness;
import com.ethiomart.shipping.domain.repository.OrderReadinessRepository;
import com.ethiomart.shipping.infrastructure.persistence.entity.OrderReadinessEntity;
import com.ethiomart.shipping.infrastructure.persistence.repository.OrderReadinessJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderReadinessRepositoryAdapter implements OrderReadinessRepository {

    private final OrderReadinessJpaRepository jpaRepository;

    public OrderReadinessRepositoryAdapter(OrderReadinessJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public OrderReadiness getOrCreate(String orderId) {
        return jpaRepository.findById(orderId)
                .map(this::toDomain)
                .orElse(new OrderReadiness(orderId));
    }

    @Override
    public OrderReadiness save(OrderReadiness readiness) {
        jpaRepository.save(new OrderReadinessEntity(
                readiness.getOrderId(),
                readiness.isPaymentCompleted(),
                readiness.isStockReserved(),
                readiness.isShipmentCreated()));
        return readiness;
    }

    private OrderReadiness toDomain(OrderReadinessEntity entity) {
        OrderReadiness readiness = new OrderReadiness(entity.getOrderId());
        if (entity.isPaymentCompleted()) {
            readiness.markPaymentCompleted();
        }
        if (entity.isStockReserved()) {
            readiness.markStockReserved();
        }
        if (entity.isShipmentCreated()) {
            readiness.markShipmentCreated();
        }
        return readiness;
    }
}
