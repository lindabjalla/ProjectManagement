package se.grouprich.projectmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import se.grouprich.projectmanagement.status.WorkItemStatus;

@Entity
public class WorkItem extends AbstractEntity
{
	@Column(nullable = false)
	private String title;

	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private WorkItemStatus status;

	protected WorkItem(){}

	public WorkItem(String title)
	{
		this.title = title;
		status = WorkItemStatus.UNSTARTED;
	}

	public User getUser()
	{
		return user;
	}

	public WorkItemStatus getStatus()
	{
		return status;
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

	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof WorkItem)
		{
			WorkItem otherWorkItem = (WorkItem) other;
			return title.equals(otherWorkItem.title) && user.equals(otherWorkItem.user) &&
				   description.equals(otherWorkItem.description) && status.equals(otherWorkItem.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += title.hashCode() * 37;
		result += user.hashCode() * 37;
		result += description.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "WorkItem [id=" + id + ", title=" + title + ", user=" + user + ", description=" + description + ", status=" + status + "]";
	}
}
