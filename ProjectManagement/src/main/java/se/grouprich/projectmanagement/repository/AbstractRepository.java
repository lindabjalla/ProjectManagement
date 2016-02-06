package se.grouprich.projectmanagement.repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import se.grouprich.projectmanagement.model.AbstractEntity;

public abstract class AbstractRepository<E extends AbstractEntity>
{
	protected E setEntityNumber(List<E> entities, E entity)
	{
		if (entities.isEmpty())
		{
			entity.setEntityNumber(1L);
		}
		else
		{
			AtomicLong count = new AtomicLong(entities.get(0).getEntityNumber());
			entity.setEntityNumber(count.incrementAndGet());
		}
		return entity;
	}
}
