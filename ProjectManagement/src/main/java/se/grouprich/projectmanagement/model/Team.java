package se.grouprich.projectmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import se.grouprich.projectmanagement.status.TeamStatus;

@Entity
public class Team extends AbstractEntity
{
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TeamStatus status;

	protected Team(){}

	public Team(final String name)
	{
		this.name = name;
		status = TeamStatus.ACTIVE;
	}

	public String getName()
	{
		return name;
	}
	
	public TeamStatus getStatus()
	{
		return status;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}

	public void setStatus(final TeamStatus status)
	{
		this.status = status;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof Team)
		{
			Team otherTeam = (Team) other;
			return controlNumber.equals(otherTeam.controlNumber) && name.equals(otherTeam.name) && status.equals(otherTeam.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += controlNumber.hashCode() * 37;
		result += name.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "Team [id=" + id + ", entityNumber=" + controlNumber + ", name=" + name + ", status=" + status + "]";
	}
}
