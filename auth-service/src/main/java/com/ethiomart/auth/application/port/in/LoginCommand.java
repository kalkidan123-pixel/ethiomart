package com.ethiomart.auth.application.port.in;

public record LoginCommand(String email, String password) {
}
