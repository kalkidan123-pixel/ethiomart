package com.ethiomart.order.domain.repository;

import com.ethiomart.order.domain.model.Order;

public interface OrderRepository {

    Order save(Order order);
}
