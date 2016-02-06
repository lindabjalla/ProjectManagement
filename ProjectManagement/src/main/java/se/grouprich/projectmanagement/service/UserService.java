package se.grouprich.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.repository.WorkItemRepository;
import se.grouprich.projectmanagement.status.UserStatus;
import se.grouprich.projectmanagement.status.WorkItemStatus;

@Service
public class UserService extends AbstractService<User, UserRepository>
{	
	private WorkItemRepository workItemRepository;
	
	@Autowired
	UserService(UserRepository superRepository, WorkItemRepository workItemRepository)
	{
		super(superRepository);
		this.workItemRepository = workItemRepository;
	}

	public User createOrUpdateUser(User user)
	{
		if (!hasValidLength(user.getUsername()))
		{
			throw new IllegalArgumentException("Username must be longer than or equal to 10 characters");
		}
		
		return createOrUpdate(user);
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
		
		return createOrUpdate(user);
	}
	
	public User fetchUserByUserNumber(String userNumber)
	{
		return superRepository.findByUserNumber(userNumber);
	}

	public User searchUserByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username)
	{
		return superRepository.findByFirstNameAndLastNameAndUsername(firstName, lastName, username);
	}

	public List<User> searchUserByFirstNameOrLastNameOrUsername(String firstName, String lastName, String username)
	{
		return superRepository.findAllByFirstNameOrLastNameOrUsername(firstName, lastName, username);
	}

	public List<User> fetchUsersByTeam(Team team)
	{
		return superRepository.findByTeam(team);
	}
	
	private boolean hasValidLength(String username)
	{
		if(username != null && !username.trim().isEmpty())
		{
			if(username.length() >= 10)
			{
				return true;
			}
		}
		return false;
	}
}
