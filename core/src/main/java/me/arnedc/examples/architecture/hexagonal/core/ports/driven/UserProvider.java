package me.arnedc.examples.architecture.hexagonal.core.ports.driven;

import java.util.Optional;
import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.users.User;

public interface UserProvider {

    Optional<User> getUser(UUID id);

}
