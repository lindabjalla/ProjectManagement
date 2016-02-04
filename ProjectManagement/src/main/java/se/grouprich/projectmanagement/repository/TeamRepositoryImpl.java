package se.grouprich.projectmanagement.repository;

import java.util.List;

import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;

public class TeamRepositoryImpl implements TeamRepositoryCustom
{
	@Override
	public User addUserToTeam(Team team, User user, UserRepository repository) throws TeamException
	{
		List<User> usersFoundByTeam = repository.findByTeam(team);
		if (usersFoundByTeam.size() >= 10)
		{
			throw new TeamException("Maximum number of users in a Team is 10");
		}
		return user.setTeam(team);
	}
}
