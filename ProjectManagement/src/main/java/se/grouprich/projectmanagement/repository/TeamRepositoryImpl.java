package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.Team;

public class TeamRepositoryImpl extends AbstractRepository<Team> implements NumberSetter<Team, TeamRepository>
{
	public Team setControlNumber(final TeamRepository teamRepository, final Team team)
	{
		if (team.getControlNumber() == null)
		{
			final List<Team> foundTeam = teamRepository.findAllOrderedByControlNumberDESC(new PageRequest(0, 1)).getContent();
			return super.setControlNumber(foundTeam, team);
		}
		return team;
	}
}
