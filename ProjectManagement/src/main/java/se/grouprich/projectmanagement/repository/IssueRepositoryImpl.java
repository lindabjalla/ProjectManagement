package se.grouprich.projectmanagement.repository;

import java.util.List;

import se.grouprich.projectmanagement.model.Issue;

public class IssueRepositoryImpl extends AbstractRepository<Issue> implements NumberSetter<Issue, IssueRepository>
{
	public Issue setEntityNumber(IssueRepository issueRepository, Issue issue)
	{
		if (issue.getEntityNumber() == null)
		{
			List<Issue> issuesOrderedByDESCNumber = issueRepository.findAllOrderedByDESCEntityNumber();
			return super.setEntityNumber(issuesOrderedByDESCNumber, issue);
		}
		return issue;
	}
}
