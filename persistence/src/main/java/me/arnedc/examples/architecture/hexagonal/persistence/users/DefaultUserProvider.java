package me.arnedc.examples.architecture.hexagonal.persistence.users;

import java.util.Optional;
import java.util.UUID;

import me.arnedc.examples.architecture.hexagonal.core.domain.users.User;
import me.arnedc.examples.architecture.hexagonal.core.ports.driven.UserProvider;
import me.arnedc.examples.architecture.hexagonal.persistence.users.dto.UserDto;
import me.arnedc.examples.architecture.hexagonal.persistence.users.repositories.UserRepository;

public class DefaultUserProvider implements UserProvider {

    private final UserRepository userRepository;

    public DefaultUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(UUID id) {
        return userRepository
                .findById(id.toString())
                .map(UserDto::toDomain);
    }

}
