package me.arnedc.examples.architecture.hexagonal.persistence.teams;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Member;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Team;
import me.arnedc.examples.architecture.hexagonal.core.ports.driven.TeamProvider;
import me.arnedc.examples.architecture.hexagonal.persistence.teams.dto.TeamDto;
import me.arnedc.examples.architecture.hexagonal.persistence.teams.repositories.TeamRepository;
import me.arnedc.examples.architecture.hexagonal.persistence.users.dto.UserDto;

import static java.util.stream.Collectors.toList;

public class DefaultTeamProvider implements TeamProvider {

    private final EntityManager entityManager;
    private final TeamRepository teamRepository;

    public DefaultTeamProvider(EntityManager entityManager, TeamRepository teamRepository) {
        this.entityManager = entityManager;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> getTeams() {
        return StreamSupport
                .stream(teamRepository.findAll().spliterator(), false)
                .map(TeamDto::toDomain)
                .collect(toList());
    }

    @Override
    public Optional<Team> getTeam(UUID id) {
        return teamRepository
                .findById(id.toString())
                .map(TeamDto::toDomain);
    }

    @Transactional
    @Override
    public Optional<Team> getTeamWithMembers(UUID id) {
        final Optional<TeamDto> optionalTeamDto = teamRepository.findById(id.toString());

        if (optionalTeamDto.isEmpty()) return Optional.empty();

        final TeamDto teamDto = optionalTeamDto.get();
        final Team team = teamDto.toDomain(teamDto.getMembers());

        return Optional.of(team);
    }

    @Override
    public void addTeam(Team team) {
        final Optional<List<Member>> optionalMembers = team.getMembers();
        if (optionalMembers.isEmpty())
            throw new IllegalArgumentException("At least one member needs to be present to create a team");

        final List<UserDto> userDtoList = optionalMembers.get()
                .stream()
                .map(Member::getId)
                .map(UUID::toString)
                .map(UserDto::new)
                .collect(toList());
        final TeamDto teamDto = new TeamDto(team.getId().toString(), team.getName(), userDtoList);

        teamRepository.save(teamDto);
    }

    @Transactional
    @Override
    public void addMemberToTeam(UUID teamId, UUID userId) {
        entityManager.createNativeQuery("INSERT INTO public.team_members(team_id, user_id) VALUES (?, ?);")
                .setParameter(1, teamId.toString())
                .setParameter(2, userId.toString())
                .executeUpdate();
    }

}
