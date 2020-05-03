package me.arnedc.examples.architecture.hexagonal.core.domain.teams;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainException;

public class TeamCreationException extends DomainException {

    public TeamCreationException(Throwable cause) {
        super("Unable to create a new team", cause);
    }

}
