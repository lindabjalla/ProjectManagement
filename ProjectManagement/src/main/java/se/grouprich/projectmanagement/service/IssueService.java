package se.grouprich.projectmanagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.repository.IssueRepository;
import se.grouprich.projectmanagement.status.WorkItemStatus;

@Service
public class IssueService extends AbstractService<Issue, IssueRepository>
{
	@Autowired
	IssueService(final IssueRepository superRepository)
	{
		super(superRepository);
	}

	@Transactional
	public Issue createAndAddToWorkItem(final WorkItem workItem, final Issue issue) throws WorkItemException
	{
		if (workItem == null)
		{
			throw new WorkItemException("WorkItem must not be null");
		}
		if (!WorkItemStatus.DONE.equals(workItem.getStatus()))
		{
			throw new WorkItemException("An Issue can only be added to a WorkItem with WorkItemStatus.DONE");
		}
		
		superRepository.setEntityNumber(superRepository, issue);
		final WorkItem unstartedWorkItem = workItem.setStatus(WorkItemStatus.UNSTARTED);
		final Issue issueAddedToWorkItem = issue.setWorkItem(unstartedWorkItem);

		return createOrUpdate(issueAddedToWorkItem);
	}

	public Issue updateIssue(final Issue issue) throws RepositoryException
	{
		if (issue.getId() == null)
		{
			throw new RepositoryException("Issue does not exist");
		}

		return createOrUpdate(issue);
	}

	public Set<WorkItem> fetchWorkItemsHavingIssue()
	{
		List<WorkItem> workItemsHavingIssue = superRepository.findWorkItemsHavingIssue();
		Set<WorkItem> nonDuplicateWorkItems = new HashSet<>();
		nonDuplicateWorkItems.addAll(workItemsHavingIssue);
		
		return nonDuplicateWorkItems;
	}
}
