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
public class TeamService //extends AbstractProjectManagementService
{
	private TeamRepository teamRepository;
	private UserRepository userRepository;
	
	@Autowired
	public TeamService(TeamRepository teamRepository, UserRepository userRepository)
	{
		this.teamRepository = teamRepository;
		this.userRepository = userRepository;
	}
	
	public TeamService(TeamRepository teamRepository)
	{
		this.teamRepository = teamRepository;
	}

	public Team findTeamById(Long id)
	{
		return teamRepository.findOne(id);
	}

	public Team createOrUpdateTeam(Team team)
	{
		return teamRepository.save(team);
	}

	public Team inactivateTeam(Team team)
	{
		team.setStatus(TeamStatus.INACTIVE);
		return teamRepository.save(team);
	}

	public Iterable<Team> fetchAllTeams()
	{
		return teamRepository.findAll();
	}
	
	@Transactional
	public User addUserToTeam(Team team, User user) throws TeamException
	{
		Team savedTeam = teamRepository.save(team);
		
		List<User> usersFoundByTeam = userRepository.findByTeam(savedTeam);
		if (usersFoundByTeam.size() >= 10)
		{
			throw new TeamException("Maximum number of users in a Team is 10");
		}
		User userWithTeam = user.setTeam(team);

		return userRepository.save(userWithTeam);
	}	
}
