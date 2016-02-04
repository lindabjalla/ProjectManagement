package se.grouprich.projectmanagement.repository;

import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;

public interface TeamRepositoryCustom
{
	User addUserToTeam(Team team, User user, UserRepository repository) throws TeamException;
}
