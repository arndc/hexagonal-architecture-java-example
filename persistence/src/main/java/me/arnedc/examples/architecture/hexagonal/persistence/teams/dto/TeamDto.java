package me.arnedc.examples.architecture.hexagonal.persistence.teams.dto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Member;
import me.arnedc.examples.architecture.hexagonal.core.domain.teams.Team;
import me.arnedc.examples.architecture.hexagonal.persistence.users.dto.UserDto;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "teams")
public class TeamDto {
    @Id
    private String id;
    private String name;
    @OneToMany(targetEntity = UserDto.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserDto> members;

    public TeamDto() {}

    public TeamDto(String id, String name, List<UserDto> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public List<UserDto> getMembers() {
        return members;
    }

    public Team toDomain() {
        return toDomain(null);
    }

    public Team toDomain(List<UserDto> members) {
        final Optional<List<Member>> optionalMembers = Optional.ofNullable(members)
                .map(userDtoList -> userDtoList
                        .stream()
                        .map(userDto -> new Member(UUID.fromString(userDto.getId()), userDto.getName()))
                        .collect(toList())
                );

        return optionalMembers.isEmpty()
                ? new Team(id, name)
                : new Team(id, name, optionalMembers.get());
    }

}
