package se.grouprich.projectmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.grouprich.projectmanagement.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{}
