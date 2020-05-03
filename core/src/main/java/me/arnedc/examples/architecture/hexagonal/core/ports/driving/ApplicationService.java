package me.arnedc.examples.architecture.hexagonal.core.ports.driving;

import java.util.Optional;
import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Team;

public interface ApplicationService {

    Team createTeam(String name, String userId);

    Optional<Team> retrieveTeamWithMembers(String id);

    UUID joinTeam(String teamId, String userId);

}
