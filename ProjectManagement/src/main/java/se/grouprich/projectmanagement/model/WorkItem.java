package se.grouprich.projectmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import se.grouprich.projectmanagement.status.WorkItemStatus;

@Entity
public class WorkItem
{
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String title;

	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Enumerated(EnumType.STRING)
	private WorkItemStatus status;

	@Embedded
	private Issue issue;

	protected WorkItem()
	{
	}

	public WorkItem(String title)
	{
		this.title = title;
	}

	public Long getId()
	{
		return id;
	}

	public WorkItem setStatus(WorkItemStatus status)
	{
		this.status = status;
		return this;
	}

	public WorkItem setDescription(String description)
	{
		this.description = description;
		return this;
	}

	public WorkItem setUser(User user)
	{
		this.user = user;
		return this;
	}

	public WorkItem setIssue(Issue issue)
	{
		this.issue = issue;
		return this;
	}

	@Override
	public String toString()
	{
		return "WorkItem [id=" + id + ", title=" + title + ", user=" + user + ", description=" + description + ", status=" + status + ", issue=" + issue + "]";
	}
}