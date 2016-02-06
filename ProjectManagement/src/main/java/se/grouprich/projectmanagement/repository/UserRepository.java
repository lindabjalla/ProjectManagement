package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;

public interface UserRepository extends CrudRepository<User, Long>, NumberSetter<User, UserRepository>
{
	User findByEntityNumber(Long entityNumber);

	User findByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username);

	List<User> findAllByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username);
	
	List<User> findByTeam(Team team);
	
	@Query("SELECT u FROM #{#entityName} u order by u.entityNumber DESC")
	List<User> findAllOrderedByDESCEntityNumber();
}
