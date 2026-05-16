package com.ethiomart.auth.application.service;

import com.ethiomart.auth.application.port.in.LoginCommand;
import com.ethiomart.auth.application.port.in.LoginResult;
import com.ethiomart.auth.application.port.in.LoginUseCase;
import com.ethiomart.auth.application.port.out.TokenGeneratorPort;
import com.ethiomart.auth.domain.exception.InvalidCredentialsException;
import com.ethiomart.auth.domain.model.User;
import com.ethiomart.auth.domain.repository.UserRepository;
import com.ethiomart.auth.domain.service.PasswordHasher;

public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenGeneratorPort tokenGenerator;

    public LoginService(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            TokenGeneratorPort tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public LoginResult login(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenGenerator.generateToken(user.getId(), user.getEmail());
        return new LoginResult(user.getId(), user.getEmail(), user.getFullName(), token);
    }
}
