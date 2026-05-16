package com.ethiomart.payment.domain.service;

import java.math.BigDecimal;

public class PaymentProcessor {

    public boolean canProcess(BigDecimal amount) {
        return amount != null && amount.signum() > 0;
    }
}
