package com.ethiomart.auth.application.port.in;

public record LoginResult(String userId, String email, String fullName, String accessToken) {
}
