package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import se.grouprich.projectmanagement.model.Issue;

public class IssueRepositoryImpl extends AbstractRepository<Issue> implements NumberSetter<Issue, IssueRepository>
{
	public Issue setEntityNumber(IssueRepository issueRepository, Issue issue)
	{
		if (issue.getEntityNumber() == null)
		{
			List<Issue> issuesOrderedByDESCEntityNumber = issueRepository.findAllOrderedByDESCEntityNumber(new PageRequest(0, 1)).getContent();
			return super.setEntityNumber(issuesOrderedByDESCEntityNumber, issue);
		}
		return issue;
	}
}
