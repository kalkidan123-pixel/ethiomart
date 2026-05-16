package com.ethiomart.auth.application.port.in;

public record RegisteredUserResult(String userId, String email, String fullName) {
}
