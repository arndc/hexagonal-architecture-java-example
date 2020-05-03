package me.arnedc.examples.architecture.hexagonal.core.domain.users;

import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainValidator;

public final class User {
    private final UUID id;
    private final String name;

    public User(String id, String name) {
        this.id = DomainValidator.isValidUuid(id, "id");
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
