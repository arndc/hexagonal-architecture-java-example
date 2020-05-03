package me.arnedc.examples.architecture.hexagonal.core.ports.driven;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Team;

public interface TeamProvider {

    List<Team> getTeams();

    Optional<Team> getTeam(UUID id);

    Optional<Team> getTeamWithMembers(UUID id);

    void addTeam(Team team);

    void addMemberToTeam(UUID teamId, UUID userId);

}
