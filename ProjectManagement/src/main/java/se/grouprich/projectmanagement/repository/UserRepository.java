package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom
{
	User findByUserNumber(String userNumber);

	User findByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username);

	List<User> findAllByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);
	
	List<User> findByTeam(Team team);
}
