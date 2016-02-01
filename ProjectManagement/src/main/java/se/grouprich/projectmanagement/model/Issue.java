package se.grouprich.projectmanagement.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class Issue
{	
	@Column(name = "issue", columnDefinition = "TEXT")
	private String description;

	protected Issue() {}
	
	public Issue(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
			Issue otherUser = (Issue) other;
			return description.equals(otherUser.description);			
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += description.hashCode() * 37;
		
		return result;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}	
}
