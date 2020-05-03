package me.arnedc.examples.architecture.hexagonal.core.application;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Team;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.TeamCreationException;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.TeamDataUnavailableException;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.TeamDomainService;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.TeamMemberAdditionException;
import me.arnedc.examples.architecture.hexagonal.core.domain.users.User;
import me.arnedc.examples.architecture.hexagonal.core.domain.users.UserDomainService;
import me.arnedc.examples.architecture.hexagonal.core.domain.users.UserNotExistsException;
import me.arnedc.examples.architecture.hexagonal.core.ports.driving.ApplicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultApplicationService implements ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(DefaultApplicationService.class);

    private final TeamDomainService teamDomainService;
    private final UserDomainService userDomainService;

    public DefaultApplicationService(TeamDomainService teamDomainService, UserDomainService userDomainService) {
        this.teamDomainService = teamDomainService;
        this.userDomainService = userDomainService;
    }

    @Override
    public Team createTeam(String teamName, String userId) {
        final CompletableFuture<Optional<User>> getUserFuture = userDomainService.getUser(userId);

        try {
            final User user = getUserFuture.get().orElseThrow(() -> new UserNotExistsException(userId));

            final CompletableFuture<Team> createTeamFuture = teamDomainService.createTeam(teamName, user);

            return createTeamFuture.get();
        } catch (InterruptedException cause) {
            Thread.currentThread().interrupt();
            log.warn("Failed to create a team (interrupted)");
            throw new TeamCreationException(cause);
        } catch (ExecutionException cause) {
            log.warn("Failed to create a team (execution)");
            throw new TeamCreationException(cause);
        }
    }

    @Override
    public Optional<Team> retrieveTeamWithMembers(String id) {
        final UUID teamId = UUID.fromString(id);

        final CompletableFuture<Optional<Team>> getTeamFuture = teamDomainService.getTeamWithMembers(teamId);

        try {
            return getTeamFuture.get();
        } catch (InterruptedException cause) {
            Thread.currentThread().interrupt();
            log.warn("Failed to retrieve team with members with team ID {} (interrupted)", id);
            throw new TeamDataUnavailableException(teamId, cause);
        } catch (ExecutionException cause) {
            log.warn("Failed to retrieve team with members with team ID {} (execution)", id);
            throw new TeamDataUnavailableException(teamId, cause);
        }
    }

    @Override
    public UUID joinTeam(String teamId, String userId) {
        final CompletableFuture<UUID> joinTeamFuture = userDomainService.getUser(userId)
                .thenApply(optionalUser -> optionalUser.orElseThrow(() -> new UserNotExistsException(userId)))
                .thenCompose(user -> teamDomainService.joinTeam(teamId, userId));

        try {
            return joinTeamFuture.get();
        } catch (InterruptedException cause) {
            Thread.currentThread().interrupt();
            log.warn("Failed to let user with ID {} join team with ID {} (interrupted)", userId, teamId);
            throw new TeamMemberAdditionException(teamId, userId, cause);
        } catch (ExecutionException cause) {
            log.warn("Failed to let user with ID {} join team with ID {} (execution)", userId, teamId);
            throw new TeamMemberAdditionException(teamId, userId, cause);
        }
    }

}
