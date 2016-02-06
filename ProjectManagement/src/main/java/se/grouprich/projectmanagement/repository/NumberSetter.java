package se.grouprich.projectmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import se.grouprich.projectmanagement.model.AbstractEntity;

public interface NumberSetter<E extends AbstractEntity, R extends CrudRepository<E, Long>>
{
	E setEntityNumber(R repository, E entity);
}
