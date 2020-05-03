package me.arnedc.examples.architecture.hexagonal.core.domain.teams;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainValidator;

public final class Team {

    private final UUID id;
    private final String name;
    private final List<Member> members;

    public Team(String name, Member member) {
        this(UUID.randomUUID().toString(), name, List.of(member));
    }

    public Team(String id, String name) {
        this(id, name, null);
    }

    public Team(String id, String name, List<Member> members) {
        this.id = DomainValidator.isValidUuid(id, "id");
        this.name = DomainValidator.isNotBlank(name, "name");
        this.members = DomainValidator.isNotNull(members, "members");
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<List<Member>> getMembers() {
        return Optional.ofNullable(members);
    }

}
