package se.grouprich.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.repository.TeamRepository;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.repository.WorkItemRepository;
import se.grouprich.projectmanagement.status.TeamStatus;
import se.grouprich.projectmanagement.status.UserStatus;
import se.grouprich.projectmanagement.status.WorkItemStatus;

@Service
public class ProjectManagementService
{
	private UserRepository userRepository;
	private TeamRepository teamRepository;
	private WorkItemRepository workItemRepository;
	// private IssueRepository issueRepository;

	@Autowired
	public ProjectManagementService(UserRepository userRepository, TeamRepository teamRepository, WorkItemRepository workItemRepository)
	{
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
		this.workItemRepository = workItemRepository;
	}

	// Gjorde denna metod temporart bara för att testa
	public User findOneUser(Long id)
	{
		return userRepository.findOne(id);
	}

	public User createOrUpdateUser(User user)
	{
		if (!userRepository.isLengthInRange(user.getUsername()))
		{
			throw new IllegalArgumentException("Username must be longer than or equal to 10 characters");
		}
		return userRepository.save(user);
	}

	@Transactional
	public User inactivateUser(User user)
	{
		user.setStatus(UserStatus.INACTIVE);
		List<WorkItem> workItemsfoundByUser = workItemRepository.findByUser(user);
		for (WorkItem workItem : workItemsfoundByUser)
		{
			workItem.setStatus(WorkItemStatus.UNSTARTED);
			workItemRepository.save(workItem);
		}
		return userRepository.save(user);
	}

	public User fetchUserByUserNumber(String userNumber)
	{
		return userRepository.findByUserNumber(userNumber);
	}

	public User searchUserByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username)
	{
		return userRepository.findByFirstNameAndLastNameAndUsername(firstName, lastName, username);
	}

	public List<User> searchUserByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username)
	{
		return userRepository.findAllByFirstNameOrLastNameOrUsername(firstName, lastName, username);
	}

	public List<User> fetchUsersByTeam(Team team)
	{
		return userRepository.findByTeam(team);
	}

	// Gjorde denna metod temporart bara för att testa
	public Team findOneTeam(Long id)
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
		user.setTeam(savedTeam);
		return userRepository.save(user);
	}

	// Gjorde denna metod temporart bara för att testa
	public WorkItem findOneWorkItem(Long id)
	{
		return workItemRepository.findOne(id);
	}

	public WorkItem createOrUpdateWorkItem(WorkItem workItem)
	{
		return workItemRepository.save(workItem);
	}

	public WorkItem changeWorkItemStatus(WorkItem workItem, WorkItemStatus status)
	{
		workItem.setStatus(status);
		return workItemRepository.save(workItem);
	}

	public List<WorkItem> removeWorkItem(WorkItem workItem)
	{
		return workItemRepository.removeById(workItem.getId());
	}

	// Det finns ingen metod som gör merge i Springs CrudRepository så därför
	// gjorde jag så här på
	// manuellt sätt. Jag fick samma problem som den här nedan.
	// http://stackoverflow.com/questions/16559407/spring-data-jpa-save-new-entity-referencing-existing-one
	@Transactional
	public WorkItem assignWorkItemToUser(User user, WorkItem workItem) throws WorkItemException
	{
		User savedUser = userRepository.save(user);
		List<WorkItem> workItemsFoundByUser = workItemRepository.findByUser(savedUser);
		if (!savedUser.getStatus().equals(UserStatus.ACTIVE))
		{
			throw new WorkItemException("A WorkItem can only be assigned to a User with UserStatus.ACTIVE");
		}
		if (workItemsFoundByUser.size() >= 5)
		{
			throw new WorkItemException("Maximum number of work items a User can have is 5");
		}
		WorkItem assignedWorkItem = workItem.setUser(savedUser);
		return workItemRepository.save(assignedWorkItem);
	}

	public List<WorkItem> fetchWorkItemsByStatus(WorkItemStatus status)
	{
		return workItemRepository.findByStatus(status);
	}

	public List<WorkItem> fetchWorkItemsForTeam(Team team)
	{
		return workItemRepository.findForTeam(team);
	}

	public List<WorkItem> fetchWorkItemsForUser(User user)
	{
		return workItemRepository.findByUser(user);
	}

	public List<WorkItem> searchWorkItemsByDescriptionContaining(String keyword)
	{
		return workItemRepository.findByDescriptionContaining(keyword);
	}

	public WorkItem createAndAddIssueToWorkItem(WorkItem workItem, Issue issue)
	{
		WorkItem workItemWithIssue = workItem.setIssue(issue);
		return workItemRepository.save(workItemWithIssue);
	}

	public WorkItem updateIssue(WorkItem workItem, Issue issue)
	{
		return createAndAddIssueToWorkItem(workItem, issue);
	}

	public List<WorkItem> fetchWorkItemsWithIssue()
	{
		return workItemRepository.findWorkItemsWithIssue();
	}
}
