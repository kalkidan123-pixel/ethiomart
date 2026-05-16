package com.ethiomart.auth.application.port.in;

public interface RegisterUserUseCase {

    RegisteredUserResult register(RegisterUserCommand command);
}
