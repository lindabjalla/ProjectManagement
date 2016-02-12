package se.grouprich.projectmanagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import se.grouprich.projectmanagement.status.TeamStatus;

@Entity
public class Team extends AbstractEntity
{
	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "team", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<User> users;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TeamStatus status;

	protected Team(){}

	public Team(final String name)
	{
		super();
		this.name = name;
		status = TeamStatus.ACTIVE;
		users = new HashSet<>();
	}

	public String getName()
	{
		return name;
	}

	public Set<User> getUsers()
	{
		return users;
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

	public Team addUser(final User user)
	{
		if (!users.contains(user))
		{
			user.setTeam(this);
			users.add(user);
		}
		return this;
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
			return controlNumber.equals(otherTeam.controlNumber) && name.equals(otherTeam.name) && users.equals(otherTeam.users) && status.equals(otherTeam.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += controlNumber.hashCode() * 37;
		result += name.hashCode() * 37;
		result += users.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "Team [id=" + id + ", controlNumber=" + controlNumber + ", name=" + name + ", status=" + status + "]";
	}
}
