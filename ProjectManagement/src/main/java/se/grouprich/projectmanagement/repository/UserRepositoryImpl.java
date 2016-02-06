package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.User;

public class UserRepositoryImpl extends AbstractRepository<User> implements NumberSetter<User, UserRepository>
{
	public User setEntityNumber(UserRepository userRepository, User user)
	{
		if (user.getEntityNumber() == null)
		{
			List<User> foundTeam = userRepository.findAllOrderedByDESCEntityNumber(new PageRequest(0, 1)).getContent();
			return super.setEntityNumber(foundTeam, user);
		}
		return user;
	}
}
