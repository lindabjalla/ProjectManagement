package se.grouprich.projectmanagement.service;

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
public class IssueService //extends AbstractProjectManagementService
{
	private IssueRepository issueRepository;
	
	@Autowired
	public IssueService(IssueRepository issueRepository)
	{
		this.issueRepository = issueRepository;
	}
	
	public Issue findIssueById(Long id)
	{
		return issueRepository.findOne(id);
	}

	@Transactional
	public Issue createAndAddIssueToWorkItem(WorkItem workItem, Issue issue) throws WorkItemException
	{
		if (workItem == null)
		{
			throw new WorkItemException("WorkItem must not be null");
		}
		if (!WorkItemStatus.DONE.equals(workItem.getStatus()))
		{
			throw new WorkItemException("An Issue can only be added to a WorkItem with WorkItemStatus.DONE");
		}
		
		Issue issueAddedToWorkItem = issue.setWorkItem(workItem);
		workItem.setStatus(WorkItemStatus.UNSTARTED);

		return issueRepository.save(issueAddedToWorkItem);
	}

	public Issue updateIssue(Issue issue) throws RepositoryException
	{
		if (issue.getId() == null)
		{
			throw new RepositoryException("Issue does not exists");
		}
		return issueRepository.save(issue);
	}
}
