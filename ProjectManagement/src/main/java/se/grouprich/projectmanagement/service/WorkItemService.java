package se.grouprich.projectmanagement.service;

import java.util.List;
import java.util.Set;

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
public class WorkItemService //extends AbstractProjectManagementService
{
	private WorkItemRepository workItemRepository;
	private IssueRepository issueRepository;
	private UserRepository userRepository;
	
	@Autowired
	public WorkItemService(WorkItemRepository workItemRepository, IssueRepository issueRepository, UserRepository userRepository)
	{
		this.workItemRepository = workItemRepository;
		this.issueRepository = issueRepository;
		this.userRepository = userRepository;
	}
	
	public WorkItem findWorkItemById(Long id)
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

	@Transactional
	public WorkItem removeWorkItem(WorkItem workItem)
	{
		issueRepository.removeByWorkItem(workItem);
		return workItemRepository.removeById(workItem.getId()).get(0);
	}
	
	public Set<WorkItem> fetchWorkItemsHavingIssue()
	{
		return issueRepository.findWorkItemsHavingIssue();
	}
	
	@Transactional
	public WorkItem assignWorkItemToUser(User user, WorkItem workItem) throws WorkItemException
	{
		User savedUser = userRepository.save(user);
		if (!UserStatus.ACTIVE.equals(savedUser.getStatus()))
		{
			throw new WorkItemException("A WorkItem can only be assigned to a User with UserStatus.ACTIVE");
		}

		List<WorkItem> workItemsFoundByUser = workItemRepository.findByUser(savedUser);
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
}