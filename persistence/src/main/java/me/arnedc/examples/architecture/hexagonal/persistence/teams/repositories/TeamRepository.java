package me.arnedc.examples.architecture.hexagonal.persistence.teams.repositories;

import me.arnedc.examples.architecture.hexagonal.persistence.teams.dto.TeamDto;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<TeamDto, String> { }
