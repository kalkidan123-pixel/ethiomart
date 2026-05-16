package com.ethiomart.shipping.domain.repository;

import com.ethiomart.shipping.domain.model.OrderReadiness;

public interface OrderReadinessRepository {

    OrderReadiness getOrCreate(String orderId);

    OrderReadiness save(OrderReadiness readiness);
}
