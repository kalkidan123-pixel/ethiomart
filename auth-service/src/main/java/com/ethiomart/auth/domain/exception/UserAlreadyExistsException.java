package com.ethiomart.auth.domain.exception;

public class UserAlreadyExistsException extends DomainException {

    public UserAlreadyExistsException(String email) {
        super("User already exists: " + email);
    }
}
