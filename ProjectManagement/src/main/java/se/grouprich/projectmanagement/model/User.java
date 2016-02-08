package se.grouprich.projectmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import se.grouprich.projectmanagement.status.UserStatus;

@Entity
public class User extends AbstractEntity
{
	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Team team;

	protected User(){}

	public User(final String username, final String password, final String firstName, final String lastName)
	{
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		status = UserStatus.ACTIVE;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public UserStatus getStatus()
	{
		return status;
	}

	public Team getTeam()
	{
		return team;
	}

	public User setUsername(final String username)
	{
		this.username = username;
		return this;
	}

	public User setPassword(final String password)
	{
		this.password = password;
		return this;
	}

	public User setStatus(final UserStatus status)
	{
		this.status = status;
		return this;
	}

	public User setTeam(final Team team)
	{
		this.team = team;
		return this;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof User)
		{
			User otherUser = (User) other;
			return controlNumber.equals(otherUser.controlNumber) && username.equals(otherUser.username) && password.equals(otherUser.password)
					&& firstName.equals(otherUser.firstName) && lastName.equals(otherUser.lastName) && status.equals(otherUser.status);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += controlNumber.hashCode() * 37;
		result += username.hashCode() * 37;
		result += password.hashCode() * 37;
		result += firstName.hashCode() * 37;
		result += lastName.hashCode() * 37;
		result += status.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", controlNumber=" + controlNumber + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", status=" + status + ", team=" + team + "]";
	}
}
