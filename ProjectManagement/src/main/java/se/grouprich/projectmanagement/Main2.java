package se.grouprich.projectmanagement;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.service.ProjectManagementService;
import se.grouprich.projectmanagement.status.WorkItemStatus;

public final class Main2
{
	public static void main(String[] args) throws TeamException, WorkItemException
	{
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.scan("se.grouprich.projectmanagement");
			context.refresh();
			ProjectManagementService service = context.getBean(ProjectManagementService.class);
			
			service.createOrUpdateTeam(new Team("Team Forest"));
			service.createOrUpdateUser(new User("SumireSumire", "12345", "Sumire", "Suzuki", "100"));
			WorkItem createdWorkItem = service.createOrUpdateWorkItem(new WorkItem("Fånga en fågel"));
			
			service.addIssueToWorkItem(createdWorkItem.setStatus(WorkItemStatus.DONE), new Issue("fågel finns inte"));
		}
	}
}
