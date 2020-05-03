package me.arnedc.examples.architecture.hexagonal;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.arnedc.examples.architecture.hexagonal.core.domain.users.User;
import me.arnedc.examples.architecture.hexagonal.persistence.users.dto.UserDto;
import me.arnedc.examples.architecture.hexagonal.persistence.users.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public CommandLineRunner populateDatabase(UserRepository userRepository) {
        final List<UserDto> defaultUsers = Stream.of(
                new User("d0f0f638-5f15-49ca-839f-b3e2df5264d5", "John Doe"),
                new User("8e09d5c6-b414-4787-82ec-fbb5440226c9", "Jane Doe")
        ).map(user -> new UserDto(user.getId(), user.getName())).collect(Collectors.toList());

        return args -> userRepository.saveAll(defaultUsers);
    }

}
