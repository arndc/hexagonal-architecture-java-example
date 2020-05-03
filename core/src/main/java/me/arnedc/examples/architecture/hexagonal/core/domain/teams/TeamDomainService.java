package me.arnedc.examples.architecture.hexagonal.core.domain.teams;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainValidator;
import me.arnedc.examples.architecture.hexagonal.core.domain.users.User;
import me.arnedc.examples.architecture.hexagonal.core.ports.driven.TeamProvider;

public class TeamDomainService {

    private final TeamProvider teamProvider;

    public TeamDomainService(TeamProvider teamProvider) {
        this.teamProvider = teamProvider;
    }

    public CompletableFuture<Team> createTeam(String name, User user) {
        return CompletableFuture
                .supplyAsync(() -> {
                    final Member firstMember = new Member(user.getId(), user.getName());
                    final Team newTeam = new Team(name, firstMember);
                    teamProvider.addTeam(newTeam);

                    return newTeam;
                })
                .exceptionally(cause -> {
                    throw new TeamCreationException(cause);
                });
    }

    public CompletableFuture<Optional<Team>> getTeam(UUID teamId) {
        return CompletableFuture
                .supplyAsync(() -> teamProvider.getTeam(teamId))
                .exceptionally(cause -> {
                    throw new TeamDataUnavailableException(teamId, cause);
                });
    }

    public CompletableFuture<Optional<Team>> getTeamWithMembers(UUID teamId) {
        return CompletableFuture
                .supplyAsync(() -> {
                    final Optional<Team> optionalTeam = teamProvider.getTeamWithMembers(teamId);
                    optionalTeam.ifPresent(team -> team
                            .getMembers()
                            .orElseThrow(() -> new IllegalStateException("Team members should be present when explicitly requesting them"))
                    );

                    return optionalTeam;
                })
                .exceptionally(cause -> {
                    throw new TeamDataUnavailableException(teamId, cause);
                });
    }

    public CompletableFuture<UUID> joinTeam(String teamId, String userId) {
        final UUID teamUuid = DomainValidator.isValidUuid(teamId, "teamId");
        final UUID userUuid = DomainValidator.isValidUuid(userId, "userId");

        return CompletableFuture
                .supplyAsync(() -> {
                    teamProvider.addMemberToTeam(teamUuid, userUuid);
                    return teamUuid;
                })
                .exceptionally(cause -> {
                    throw new TeamMemberAdditionException(teamUuid, userUuid, cause);
                });
    }

}
