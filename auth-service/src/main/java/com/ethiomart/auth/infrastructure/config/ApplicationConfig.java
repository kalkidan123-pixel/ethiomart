package com.ethiomart.auth.infrastructure.config;

import com.ethiomart.auth.application.port.in.LoginUseCase;
import com.ethiomart.auth.application.port.in.RegisterUserUseCase;
import com.ethiomart.auth.application.port.out.EventPublisherPort;
import com.ethiomart.auth.application.port.out.TokenGeneratorPort;
import com.ethiomart.auth.application.service.LoginService;
import com.ethiomart.auth.application.service.RegisterUserService;
import com.ethiomart.auth.domain.repository.UserRepository;
import com.ethiomart.auth.domain.service.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            EventPublisherPort eventPublisher) {
        return new RegisterUserService(userRepository, passwordHasher, eventPublisher);
    }

    @Bean
    LoginUseCase loginUseCase(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            TokenGeneratorPort tokenGenerator) {
        return new LoginService(userRepository, passwordHasher, tokenGenerator);
    }
}
