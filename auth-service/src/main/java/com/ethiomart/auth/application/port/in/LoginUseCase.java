package com.ethiomart.auth.application.port.in;

public interface LoginUseCase {

    LoginResult login(LoginCommand command);
}
