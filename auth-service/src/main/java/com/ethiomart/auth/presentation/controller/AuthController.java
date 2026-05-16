package com.ethiomart.auth.presentation.controller;

import com.ethiomart.auth.application.port.in.LoginCommand;
import com.ethiomart.auth.application.port.in.LoginResult;
import com.ethiomart.auth.application.port.in.LoginUseCase;
import com.ethiomart.auth.application.port.in.RegisterUserCommand;
import com.ethiomart.auth.application.port.in.RegisterUserUseCase;
import com.ethiomart.auth.application.port.in.RegisteredUserResult;
import com.ethiomart.auth.presentation.dto.LoginRequest;
import com.ethiomart.auth.presentation.dto.LoginResponse;
import com.ethiomart.auth.presentation.dto.RegisterRequest;
import com.ethiomart.auth.presentation.dto.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "User registration and login")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisteredUserResult result = registerUserUseCase.register(
                new RegisterUserCommand(request.email(), request.fullName(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse(result.userId(), result.email(), result.fullName()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = loginUseCase.login(new LoginCommand(request.email(), request.password()));
        return ResponseEntity.ok(new LoginResponse(
                result.userId(),
                result.email(),
                result.fullName(),
                result.accessToken()));
    }
}
