package se.grouprich.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.repository.TeamRepository;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.status.TeamStatus;

@Service
public class TeamService extends AbstractService<Team, TeamRepository>
{
	private UserRepository userRepository;

	@Autowired
	TeamService(final TeamRepository superRepository, final UserRepository userRepository)
	{
		super(superRepository);
		this.userRepository = userRepository;
	}

	public Team createOrUpdate(Team team)
	{
		superRepository.setEntityNumber(superRepository, team);
		return super.createOrUpdate(team);
	}

	public Team inactivateTeam(final Team team)
	{
		team.setStatus(TeamStatus.INACTIVE);
		return createOrUpdate(team);
	}

	@Transactional
	public User addUserToTeam(final Team team, final User user) throws TeamException
	{
		final Team savedTeam = createOrUpdate(team);

		List<User> usersFoundByTeam = userRepository.findByTeam(savedTeam);
		if (usersFoundByTeam.size() >= 10)
		{
			throw new TeamException("Maximum number of users in a Team is 10");
		}

		userRepository.setEntityNumber(userRepository, user);
		User userWithTeam = user.setTeam(savedTeam);

		return userRepository.save(userWithTeam);
	}
}
