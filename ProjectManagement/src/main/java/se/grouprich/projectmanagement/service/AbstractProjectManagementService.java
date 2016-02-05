package se.grouprich.projectmanagement.service;

import javax.persistence.MappedSuperclass;

import org.springframework.data.repository.CrudRepository;

import se.grouprich.projectmanagement.model.AbstractEntity;
import se.grouprich.projectmanagement.model.Issue;

@MappedSuperclass
public abstract class AbstractProjectManagementService<E extends AbstractEntity> implements CrudRepository<E, Long>
{	
	
}
