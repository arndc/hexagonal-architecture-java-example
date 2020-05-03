package me.arnedc.examples.architecture.hexagonal.core.domain.teams;

import java.util.UUID;

public final class Member {

    private final UUID id;
    private final String name;

    public Member(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
