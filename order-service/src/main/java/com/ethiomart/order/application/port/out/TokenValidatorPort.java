package com.ethiomart.order.application.port.out;

public interface TokenValidatorPort {

    String validateAndExtractUserId(String bearerToken);
}
