package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import se.grouprich.projectmanagement.model.WorkItem;

public class WorkItemRepositoryImpl extends AbstractRepository<WorkItem> implements NumberSetter<WorkItem, WorkItemRepository>
{
	public WorkItem setEntityNumber(WorkItemRepository workItemRepository, WorkItem workItem)
	{
		if (workItem.getEntityNumber() == null)
		{
			List<WorkItem> foundWorkItem = workItemRepository.findAllOrderedByDESCEntityNumber(new PageRequest(0, 1)).getContent();
			return super.setEntityNumber(foundWorkItem, workItem);
		}
		return workItem;
	}
}