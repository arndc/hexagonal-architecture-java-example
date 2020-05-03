package me.arnedc.examples.architecture.hexagonal.core.domain.users;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import me.arnedc.examples.architecture.hexagonal.core.domain.DomainValidator;
import me.arnedc.examples.architecture.hexagonal.core.ports.driven.UserProvider;

public class UserDomainService {

    private final UserProvider userProvider;

    public UserDomainService(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public CompletableFuture<Optional<User>> getUser(String id) {
        final UUID userId = DomainValidator.isValidUuid(id, "userId");

        return CompletableFuture
                .supplyAsync(() -> userProvider.getUser(userId))
                .exceptionally(cause -> {
                    throw new UserDataUnavailableException(userId, cause);
                });
    }

}
