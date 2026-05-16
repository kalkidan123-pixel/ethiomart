package com.ethiomart.auth.domain.model;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final String id;
    private final String email;
    private final String fullName;
    private final String passwordHash;

    public User(String email, String fullName, String passwordHash) {
        this(UUID.randomUUID().toString(), email, fullName, passwordHash);
    }

    public User(String id, String email, String fullName, String passwordHash) {
        this.id = Objects.requireNonNull(id, "id");
        this.email = Objects.requireNonNull(email, "email");
        this.fullName = Objects.requireNonNull(fullName, "fullName");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash");
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
