package se.grouprich.projectmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import se.grouprich.projectmanagement.status.UserStatus;

@Entity
public class User
{
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String username;

	@Column
	private String password;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column(unique = true)
	private String userNumber;

	@Column
	private UserStatus status;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Team team;

	protected User()
	{
	}

	// skapat den här konstruktorn temporart för att testa lättare
	public User(String username)
	{
		this.username = username;
	}

	public User(String username, String password, String firstName, String lastName, String userNumber)
	{
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userNumber = userNumber;
		status = UserStatus.ACTIVE;
	}

	public Long getId()
	{
		return id;
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

	public String getUserNumber()
	{
		return userNumber;
	}

	public UserStatus getStatus()
	{
		return status;
	}

	public Team getTeam()
	{
		return team;
	}

	public void setStatus(UserStatus status)
	{
		this.status = status;
	}

	public void setTeam(Team team)
	{
		this.team = team;
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
			return username.equals(otherUser.username) && password.equals(otherUser.password) &&
					firstName.equals(otherUser.firstName) && lastName.equals(otherUser.lastName) &&
					userNumber.equals(otherUser.userNumber) && status.equals(otherUser.status) &&
					team.equals(otherUser.team);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += username.hashCode() * 37;
		result += password.hashCode() * 37;
		result += firstName.hashCode() * 37;
		result += lastName.hashCode() * 37;
		result += userNumber.hashCode() * 37;
		result += status.hashCode() * 37;
		result += team.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", userNumber="
				+ userNumber + ", status=" + status + ", team=" + team + "]";
	}
}
