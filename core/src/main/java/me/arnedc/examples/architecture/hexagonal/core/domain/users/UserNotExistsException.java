package me.arnedc.examples.architecture.hexagonal.core.domain.users;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainException;

public class UserNotExistsException extends DomainException {

    public UserNotExistsException(String userId) {
        super("No user exists with ID " + userId);
    }

}
