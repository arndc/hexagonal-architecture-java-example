package me.arnedc.examples.architecture.hexagonal.core.domain.teams;

import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainException;

public class TeamMemberAdditionException extends DomainException {

    public TeamMemberAdditionException(String teamId, String userId, Throwable cause) {
        super("Unable to add user with ID " + userId + " to team with ID " + teamId, cause);
    }

    public TeamMemberAdditionException(UUID teamId, UUID userId, Throwable cause) {
        super("Unable to add user with ID " + userId + " to team with ID " + teamId, cause);
    }

}
