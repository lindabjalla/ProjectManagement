package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.Issue;

public class IssueRepositoryImpl extends AbstractRepository<Issue> implements NumberSetter<Issue, IssueRepository>
{
	public Issue setControlNumber(final IssueRepository issueRepository, final Issue issue)
	{
		if (issue.getControlNumber() == null)
		{
			final List<Issue> foundIssue = issueRepository.findAllOrderedByControlNumberDESC(new PageRequest(0, 1)).getContent();
			return super.setControlNumber(foundIssue, issue);
		}
		return issue;
	}
}
