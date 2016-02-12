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
	UserService(final UserRepository superRepository, final WorkItemRepository workItemRepository)
	{
		super(superRepository);
		this.workItemRepository = workItemRepository;
	}

	public User createOrUpdate(final User user)
	{
		if (!hasValidLength(user.getUsername()))
		{
			throw new IllegalArgumentException("Username must be longer than or equal to 10 characters");
		}
		return super.createOrUpdate(user);
	}

	@Transactional
	public User inactivateUser(final User user)
	{
		user.setStatus(UserStatus.INACTIVE);

		final List<WorkItem> workItemsfoundByUser = workItemRepository.findByUser(user);
		for (WorkItem workItem : workItemsfoundByUser)
		{
			workItem.setStatus(WorkItemStatus.UNSTARTED);
			workItemRepository.save(workItem);
		}

		return createOrUpdate(user);
	}

	public User findByControlNumber(final Long controlNumber)
	{
		return superRepository.findByControlNumber(controlNumber);
	}

	public User searchUserByFirstNameAndLastNameAndUsername(final String firstName, final String lastName, final String username)
	{
		return superRepository.findByFirstNameAndLastNameAndUsername(firstName, lastName, username);
	}

	public List<User> searchUserByFirstNameOrLastNameOrUsername(final String firstName, final String lastName, final String username)
	{
		return superRepository.findAllByFirstNameOrLastNameOrUsername(firstName, lastName, username);
	}

	public List<User> findByTeam(final Team team)
	{
		return superRepository.findByTeam(team);
	}

	private boolean hasValidLength(final String username)
	{
		if (username != null && !username.trim().isEmpty())
		{
			if (username.length() >= 10)
			{
				return true;
			}
		}
		return false;
	}
}
