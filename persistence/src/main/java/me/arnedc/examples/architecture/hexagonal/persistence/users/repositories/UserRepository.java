package me.arnedc.examples.architecture.hexagonal.persistence.users.repositories;

import me.arnedc.examples.architecture.hexagonal.persistence.users.dto.UserDto;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDto, String> { }
