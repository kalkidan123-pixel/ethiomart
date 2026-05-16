package com.ethiomart.auth.application.port.out;

public interface TokenGeneratorPort {

    String generateToken(String userId, String email);
}
