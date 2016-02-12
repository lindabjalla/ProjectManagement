package se.grouprich.projectmanagement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity
{
	@Id
	@GeneratedValue
	Long id;

	@Column(nullable = false, unique = true)
	Long controlNumber;

	protected AbstractEntity()
	{
		setControlNumber();
	}

	public Long getId()
	{
		return id;
	}

	public Long getControlNumber()
	{
		return controlNumber;
	}

	public void setControlNumber()
	{
		Date now = new Date();
		Long time = now.getTime();
		controlNumber = time;
	}
}
