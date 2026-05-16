package com.ethiomart.auth.application.port.in;

public record RegisterUserCommand(String email, String fullName, String password) {
}
