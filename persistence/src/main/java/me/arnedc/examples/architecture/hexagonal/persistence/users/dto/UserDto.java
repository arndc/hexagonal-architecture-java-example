package me.arnedc.examples.architecture.hexagonal.persistence.users.dto;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import me.arnedc.examples.architecture.hexagonal.core.domain.users.User;

@Entity
@Table(name = "users")
public class UserDto {
    @Id
    private String id;
    private String name;

    public UserDto() {}

    public UserDto(String id) {
        this.id = id;
    }

    public UserDto(UUID id, String name) {
        this.id = id.toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User toDomain() {
        return new User(id, name);
    }

}
