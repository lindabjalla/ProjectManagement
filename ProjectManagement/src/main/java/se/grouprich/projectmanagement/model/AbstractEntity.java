package se.grouprich.projectmanagement.model;

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
	
	public Long getId()
	{
		return id;
	}
	
	public Long getControlNumber()
	{
		return controlNumber;
	}
	
	public void setControlNumber(final Long controlNumber)
	{
		this.controlNumber = controlNumber;
	}
}
