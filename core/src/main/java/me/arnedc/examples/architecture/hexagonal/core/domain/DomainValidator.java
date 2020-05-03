package me.arnedc.examples.architecture.hexagonal.core.domain;

import java.util.UUID;

public final class DomainValidator {

    private DomainValidator() {}

    public static String isNotBlank(final String value, final String field) {
        if (isNotNull(value, field).isBlank()) throw new IllegalArgumentException("[" + field + "] cannot be blank");

        return value;
    }

    public static <T> T isNotNull(final T value, final String field) {
        if (value == null) throw new IllegalArgumentException("[" + field + "] cannot be null");

        return value;
    }

    public static UUID isValidUuid(String value, String field) {
        try {
            final UUID uuid = UUID.fromString(isNotNull(value, field));
            if (!uuid.toString().equals(value)) throw new IllegalArgumentException();

            return uuid;
        } catch (IllegalArgumentException cause) {
            throw new IllegalArgumentException("[" + field + "] is not a valid UUID");
        }
    }

}
