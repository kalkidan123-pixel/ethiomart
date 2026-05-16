package com.ethiomart.auth.presentation.dto;

public record LoginResponse(String userId, String email, String fullName, String accessToken) {
}
