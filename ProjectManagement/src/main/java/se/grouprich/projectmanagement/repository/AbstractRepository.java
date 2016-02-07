package se.grouprich.projectmanagement.repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import se.grouprich.projectmanagement.model.AbstractEntity;

public abstract class AbstractRepository<E extends AbstractEntity>
{
	protected E setControlNumber(final List<E> entities, final E entity)
	{
		if (entities.isEmpty())
		{
			entity.setControlNumber(1L);
		}
		else
		{
			AtomicLong count = new AtomicLong(entities.get(0).getControlNumber());
			entity.setControlNumber(count.incrementAndGet());
		}
		return entity;
	}
}
