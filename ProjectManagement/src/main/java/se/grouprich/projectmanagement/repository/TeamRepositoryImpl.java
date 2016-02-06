package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.Team;

public class TeamRepositoryImpl extends AbstractRepository<Team> implements NumberSetter<Team, TeamRepository>
{
	public Team setEntityNumber(TeamRepository teamRepository, Team team)
	{
		if (team.getEntityNumber() == null)
		{
			List<Team> foundTeam = teamRepository.findAllOrderedByDESCEntityNumber(new PageRequest(0, 1)).getContent();
			return super.setEntityNumber(foundTeam, team);
		}
		return team;
	}
}
