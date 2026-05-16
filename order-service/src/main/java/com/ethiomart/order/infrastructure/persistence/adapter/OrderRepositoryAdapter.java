package com.ethiomart.order.infrastructure.persistence.adapter;

import com.ethiomart.order.domain.model.Order;
import com.ethiomart.order.domain.repository.OrderRepository;
import com.ethiomart.order.infrastructure.persistence.entity.OrderEntity;
import com.ethiomart.order.infrastructure.persistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    public OrderRepositoryAdapter(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = new OrderEntity(
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getQuantity(),
                order.getAmount());
        jpaRepository.save(entity);
        return order;
    }
}
