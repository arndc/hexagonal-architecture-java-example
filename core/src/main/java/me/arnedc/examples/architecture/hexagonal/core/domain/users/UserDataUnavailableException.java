package me.arnedc.examples.architecture.hexagonal.core.domain.users;

import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainException;

public class UserDataUnavailableException extends DomainException {

    public UserDataUnavailableException(UUID userId, Throwable cause) {
        super("Data of user with ID " + userId + " is currently not available", cause);
    }

}
