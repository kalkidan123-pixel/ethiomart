package com.ethiomart.auth.application.service;

import com.ethiomart.auth.application.port.in.RegisterUserCommand;
import com.ethiomart.auth.application.port.in.RegisterUserUseCase;
import com.ethiomart.auth.application.port.in.RegisteredUserResult;
import com.ethiomart.auth.application.port.out.EventPublisherPort;
import com.ethiomart.auth.domain.exception.UserAlreadyExistsException;
import com.ethiomart.auth.domain.model.User;
import com.ethiomart.auth.domain.repository.UserRepository;
import com.ethiomart.auth.domain.service.PasswordHasher;
import com.ethiomart.events.UserRegisteredEvent;

import java.time.Instant;
import java.util.UUID;

public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final EventPublisherPort eventPublisher;

    public RegisterUserService(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            EventPublisherPort eventPublisher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public RegisteredUserResult register(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException(command.email());
        }

        String hash = passwordHasher.hash(command.password());
        User user = new User(command.email(), command.fullName(), hash);
        User saved = userRepository.save(user);

        eventPublisher.publishUserRegistered(new UserRegisteredEvent(
                UUID.randomUUID().toString(),
                saved.getId(),
                saved.getEmail(),
                saved.getFullName(),
                Instant.now()));

        return new RegisteredUserResult(saved.getId(), saved.getEmail(), saved.getFullName());
    }
}
