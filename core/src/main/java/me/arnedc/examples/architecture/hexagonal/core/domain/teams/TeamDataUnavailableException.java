package me.arnedc.examples.architecture.hexagonal.core.domain.teams;

import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainException;

public class TeamDataUnavailableException extends DomainException {

    public TeamDataUnavailableException(UUID id, Throwable cause) {
        super("Data of team with ID " + id + " is currently not available", cause);
    }

}
