package se.grouprich.projectmanagement.repository;

import java.util.List;

import se.grouprich.projectmanagement.model.WorkItem;

public class WorkItemRepositoryImpl extends AbstractRepository<WorkItem> implements NumberSetter<WorkItem, WorkItemRepository>
{
	public WorkItem setEntityNumber(WorkItemRepository workItemRepository, WorkItem workItem)
	{
		if (workItem.getEntityNumber() == null)
		{
			List<WorkItem> workItemsOrderedByDESCNumber = workItemRepository.findAllOrderedByDESCEntityNumber();
			return super.setEntityNumber(workItemsOrderedByDESCNumber, workItem);
		}
		return workItem;
	}
}