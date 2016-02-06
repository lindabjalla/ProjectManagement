package se.grouprich.projectmanagement.repository;

import java.util.List;

import se.grouprich.projectmanagement.model.User;

public class UserRepositoryImpl extends AbstractRepository<User> implements NumberSetter<User, UserRepository>
{
	public User setEntityNumber(UserRepository userRepository, User user)
	{
		if (user.getEntityNumber() == null)
		{
			List<User> usersOrderedByDESCNumber = userRepository.findAllOrderedByDESCEntityNumber();
			return super.setEntityNumber(usersOrderedByDESCNumber, user);
		}
		return user;
	}
}
