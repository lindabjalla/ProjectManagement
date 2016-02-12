package se.grouprich.projectmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.repository.TeamRepository;
import se.grouprich.projectmanagement.status.TeamStatus;

@Service
public class TeamService extends AbstractService<Team, TeamRepository>
{
	@Autowired
	TeamService(final TeamRepository superRepository)
	{
		super(superRepository);
	}

	public Team createOrUpdate(final Team team)
	{
		return super.createOrUpdate(team);
	}

	public Team inactivateTeam(final Team team)
	{
		team.setStatus(TeamStatus.INACTIVE);
		return createOrUpdate(team);
	}

	@Transactional
	public Team addUserToTeam(final Team team, final User user) throws TeamException, RepositoryException
	{
		if (user.getTeam() != null)
		{
			throw new TeamException("User is already in a Team. A User can only be in one Team at a time");
		}
		if (team.getUsers().size() >= 10)
		{
			throw new TeamException("Maximum number of users in a Team is 10");
		}

		final Team teamUserAdded = team.addUser(user);
		return createOrUpdate(teamUserAdded);
	}
}
