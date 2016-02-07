package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.User;

public class UserRepositoryImpl extends AbstractRepository<User> implements NumberSetter<User, UserRepository>
{
	public User setControlNumber(final UserRepository userRepository, final User user)
	{
		if (user.getControlNumber() == null)
		{
			final List<User> foundTeam = userRepository.findAllOrderedByControlNumberDESC(new PageRequest(0, 1)).getContent();
			return super.setControlNumber(foundTeam, user);
		}
		return user;
	}
}
