package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.WorkItem;

public class WorkItemRepositoryImpl extends AbstractRepository<WorkItem> implements NumberSetter<WorkItem, WorkItemRepository>
{
	public WorkItem setControlNumber(final WorkItemRepository workItemRepository, final WorkItem workItem)
	{
		if (workItem.getControlNumber() == null)
		{
			final List<WorkItem> foundWorkItem = workItemRepository.findAllOrderedByControlNumberDESC(new PageRequest(0, 1)).getContent();
			return super.setControlNumber(foundWorkItem, workItem);
		}
		return workItem;
	}
}
