package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.Issue;

public class IssueRepositoryImpl extends AbstractRepository<Issue> implements NumberSetter<Issue, IssueRepository>
{
	public Issue setEntityNumber(IssueRepository issueRepository, Issue issue)
	{
		if (issue.getEntityNumber() == null)
		{
			List<Issue> foundIssue = issueRepository.findAllOrderedByDESCEntityNumber(new PageRequest(0, 1)).getContent();
			return super.setEntityNumber(foundIssue, issue);
		}
		return issue;
	}
}
