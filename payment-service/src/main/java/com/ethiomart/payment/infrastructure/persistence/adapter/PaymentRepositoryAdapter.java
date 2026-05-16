package com.ethiomart.payment.infrastructure.persistence.adapter;

import com.ethiomart.payment.domain.model.Payment;
import com.ethiomart.payment.domain.repository.PaymentRepository;
import com.ethiomart.payment.infrastructure.persistence.entity.PaymentEntity;
import com.ethiomart.payment.infrastructure.persistence.repository.PaymentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    public PaymentRepositoryAdapter(PaymentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = new PaymentEntity(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus().name());
        jpaRepository.save(entity);
        return payment;
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        return jpaRepository.findByOrderId(orderId).map(e ->
                new Payment(e.getId(), e.getOrderId(), e.getAmount(), Payment.Status.valueOf(e.getStatus())));
    }
}
