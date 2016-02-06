package se.grouprich.projectmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Issue extends AbstractEntity
{
	@Column(name = "issue", columnDefinition = "TEXT", nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(nullable = false)
	private WorkItem workItem;

	protected Issue(){}

	public Issue(final String description)
	{
		this.description = description;
	}

	public Long getId()
	{
		return id;
	}

	public String getDescription()
	{
		return description;
	}

	public WorkItem getWorkItem()
	{
		return workItem;
	}

	public Issue setDescription(final String description)
	{
		this.description = description;
		return this;
	}

	public Issue setWorkItem(final WorkItem workItem)
	{
		this.workItem = workItem;
		return this;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof Issue)
		{
			Issue otherIssue = (Issue) other;
			return entityNumber.equals(otherIssue.entityNumber) && description.equals(otherIssue.description);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += entityNumber.hashCode() * 37;
		result += description.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "Issue [id=" + id + ", entityNumber=" + entityNumber + ", description=" + description + ", workItem=" + workItem + "]";
	}
}
