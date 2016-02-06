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
	Long entityNumber;
	
	public Long getId()
	{
		return id;
	}
	
	public Long getEntityNumber()
	{
		return entityNumber;
	}
	
	public void setEntityNumber(Long entityNumber)
	{
		this.entityNumber = entityNumber;
	}
}
