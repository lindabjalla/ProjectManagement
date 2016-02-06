package se.grouprich.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.repository.IssueRepository;
import se.grouprich.projectmanagement.repository.UserRepository;
import se.grouprich.projectmanagement.repository.WorkItemRepository;
import se.grouprich.projectmanagement.status.UserStatus;
import se.grouprich.projectmanagement.status.WorkItemStatus;

@Service
public class WorkItemService extends AbstractService<WorkItem, WorkItemRepository>
{
	private IssueRepository issueRepository;
	private UserRepository userRepository;

	@Autowired
	WorkItemService(WorkItemRepository superRepository, IssueRepository issueRepository, UserRepository userRepository)
	{
		super(superRepository);
		this.issueRepository = issueRepository;
		this.userRepository = userRepository;
	}

	public WorkItem createOrUpdate(WorkItem workItem)
	{
		superRepository.setEntityNumber(superRepository, workItem);
		return super.createOrUpdate(workItem);
	}

	public WorkItem changeWorkItemStatus(WorkItem workItem, WorkItemStatus status)
	{
		workItem.setStatus(status);
		return createOrUpdate(workItem);
	}

	@Transactional
	public WorkItem removeWorkItem(WorkItem workItem)
	{
		issueRepository.removeByWorkItem(workItem);
		return superRepository.removeById(workItem.getId()).get(0);
	}

	@Transactional
	public WorkItem assignWorkItemToUser(User user, WorkItem workItem) throws WorkItemException
	{
		User savedUser = userRepository.save(user);
		if (!UserStatus.ACTIVE.equals(savedUser.getStatus()))
		{
			throw new WorkItemException("A WorkItem can only be assigned to a User with UserStatus.ACTIVE");
		}

		List<WorkItem> workItemsFoundByUser = superRepository.findByUser(savedUser);
		if (workItemsFoundByUser.size() >= 5)
		{
			throw new WorkItemException("Maximum number of work items a User can have is 5");
		}

		WorkItem assignedWorkItem = workItem.setUser(savedUser);
		return createOrUpdate(assignedWorkItem);
	}

	public List<WorkItem> fetchWorkItemsByStatus(WorkItemStatus status)
	{
		return superRepository.findByStatus(status);
	}

	public List<WorkItem> fetchWorkItemsForTeam(Team team)
	{
		return superRepository.findByTeam(team);
	}

	public List<WorkItem> fetchWorkItemsForUser(User user)
	{
		return superRepository.findByUser(user);
	}

	public List<WorkItem> searchWorkItemsByDescriptionContaining(String keyword)
	{
		return superRepository.findByDescriptionContaining(keyword);
	}
}
