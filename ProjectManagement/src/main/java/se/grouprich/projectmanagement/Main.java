package se.grouprich.projectmanagement;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.grouprich.projectmanagement.exception.RepositoryException;
import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.service.ProjectManagementService;
import se.grouprich.projectmanagement.status.UserStatus;
import se.grouprich.projectmanagement.status.WorkItemStatus;

public final class Main
{
	public static void main(String[] args) throws TeamException, WorkItemException, RepositoryException
	{
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.scan("se.grouprich.projectmanagement");
			context.refresh();
			ProjectManagementService service = context.getBean(ProjectManagementService.class);

			User user3 = new User("Henrikkkkkkkkkkkkkkkkkkkkkkkk");
			
//			Testar om exceptioin kastas när workItem tilldelas till INACTIVE user 
//			User user3 = new User("Henrikkkkkkkkkkkkkkkkkkkkkkkk").setStatus(UserStatus.INACTIVE);
			
			WorkItem workItemForHenrik = service.assignWorkItemToUser(user3, new WorkItem("diska"));
			System.out.println("workItemForHenrik: " + workItemForHenrik);
			System.out.println();
			
			Team team5 = new Team("team5");
			System.out.println("team5: " + team5);
			System.out.println();
			
			User henrik = service.findUserById(1L);
			User henrikJoinedTeam = service.addUserToTeam(team5, henrik);
			System.out.println("henriksTeam: " + henrikJoinedTeam.getTeam());
			System.out.println();
			
			Team teamB = new Team("teamB");
			Team teamBSaved = service.createOrUpdateTeam(teamB);
			System.out.println("teamBSaved: " + teamBSaved);
			System.out.println();
			
			Team teamBInactivated = service.inactivateTeam(teamBSaved);
			System.out.println(teamBInactivated);
			System.out.println();
			
			Iterable<Team> fetchAllTeams = service.fetchAllTeams();		
			System.out.println("fetchAllTeams: " + fetchAllTeams);
			System.out.println();
			
			WorkItem workItem1 = new WorkItem("Hundpromenad");
			workItem1.setDescription("Promenera med en hund som heter Panda");
			WorkItem workItem1Saved = service.createOrUpdateWorkItem(workItem1);
			System.out.println("workItem1Saved: " + workItem1Saved);
			System.out.println();
			
			WorkItem workItem1StatusChanged = service.changeWorkItemStatus(workItem1Saved, WorkItemStatus.STARTED);
			System.out.println("workItem1StatusChanged: " + workItem1StatusChanged);
			System.out.println();
			
			WorkItem workItemRemoved = service.removeWorkItem(workItem1StatusChanged);
			System.out.println("workItemRemoved: " + workItemRemoved);
			System.out.println();
			
			WorkItem workItem2 = new WorkItem("Kattpromenad").setStatus(WorkItemStatus.UNSTARTED).setDescription("Promenera med en katt två timmar");;
			User user = new User("Nekoooooooooooooooooo");
			WorkItem workItemAssignedToUser = service.assignWorkItemToUser(user, workItem2);
			System.out.println("workItemAssignedToUser: " + workItemAssignedToUser);
			System.out.println();
			
			List<WorkItem> workItemsFetchedByStatus = service.fetchWorkItemsByStatus(WorkItemStatus.UNSTARTED);
			System.out.println("workItemsFetchedByStatus: " + workItemsFetchedByStatus);
			System.out.println();
			
			Team team7 = new Team("Team7");
			User user2 = new User("Marimekkooooooooooooooo");
			WorkItem workItem3 = new WorkItem("Lek med katt").setDescription("Lek med katten med bollen");
			service.addUserToTeam(team7, user2);
			service.assignWorkItemToUser(user2, workItem3);
			System.out.println("workItem3: " + workItem3);
			System.out.println();
			
			User hanaJoinedTeam7 = service.addUserToTeam(team7, new User("Hanaaaaaaaaaaaaaaaa"));
			System.out.println("hanaJoinedTeam7: " + hanaJoinedTeam7);
			System.out.println();
			
			Team foundTeam = service.findTeamById(8L);
			List<WorkItem> workItemsFetchedForTeam = service.fetchWorkItemsForTeam(foundTeam);
			System.out.println("workItemsFetchedForTeam: " + workItemsFetchedForTeam);
			System.out.println();
			
			User foundUser = service.findUserById(7L);
			
			List<WorkItem> workItemsForUser = service.fetchWorkItemsForUser(foundUser);
			System.out.println("workItemsForUser: " + workItemsForUser);
			System.out.println();
			
			List<WorkItem> workItemsSearchedByKatt = service.searchWorkItemsByDescriptionContaining("katt");
			System.out.println("workItemsSearchedByKatt: " + workItemsSearchedByKatt);
			System.out.println();
			
			WorkItem foundWorkItem = service.findWorkItemById(10L).setStatus(WorkItemStatus.DONE);
			Issue issueAssignedToWorkItem = service.createAndAddIssueToWorkItem(foundWorkItem, new Issue("katten vill leka mer"));
			System.out.println("issueAssignedToWorkItem: " + issueAssignedToWorkItem);
			System.out.println();
			
			Issue foundIssue = service.findIssueById(12L);
			Issue updatedIssue = service.updateIssue(foundIssue.setDescription("katten vill äta nu"));
			System.out.println("workItemWithUpdatedIssue: " + updatedIssue);
			System.out.println();
			
			service.createAndAddIssueToWorkItem(foundWorkItem.setStatus(WorkItemStatus.DONE), new Issue("katten är nu trött"));
			Set<WorkItem> workItemsWithIssue = service.fetchWorkItemsHavingIssue();
			System.out.println("workItemsWithIssue: " + workItemsWithIssue);
			System.out.println();
			
			User createdUser = service.createOrUpdateUser(new User("Midoriiiiiiiiiiiiiiiiiiii", "12rI", "Midori", "Ayanokoji", "100"));
			System.out.println("createdUser: " + createdUser);
			System.out.println();
			
			User userFoundByUserNumber = service.fetchUserByUserNumber("100");
			System.out.println("userFoundByUserNumber: " + userFoundByUserNumber);
			System.out.println();
			
			User userFoundByFirstNameAndLastNameAndUsername = service.searchUserByFirstNameAndLastNameAndUsername("Midoriiiiiiiiiiiiiiiiiiii", "Ayanokoji", "Midori");
			System.out.println("MidoriFoundByFirstname and lastname and username: " + userFoundByFirstNameAndLastNameAndUsername);
			System.out.println();
			
			List<User> userFoundByFirstNameOrLastNameOrUsername = service.searchUserByFirstNameOrLastNameOrUsername(" ", "Ayanokoji","Henrik"); 
			System.out.println("MidoriFoundByFirstname or lastname or username: " + userFoundByFirstNameOrLastNameOrUsername);
			System.out.println();
			
			List<User> usersFetchByTeam = service.fetchUsersByTeam(foundTeam);
			System.out.println("fetch users by team: " + usersFetchByTeam);
			System.out.println();
			
			User foundUser2 = service.findUserById(9L);
			User userInactivated = service.inactivateUser(foundUser2);
			System.out.println("userInactivated: " + userInactivated);
			System.out.println();
			
//			Testar om exception kastas när ett Team har flera än 10 medlemmar.
//			Om du inte vill få TeamException(Team has already 10 members) kommentera bort den här for-loopen.
//			for (int i = 0; i < 10; i++)
//			{
//				service.addUserToTeam(foundTeam, new User("Sumireeeeeeeeeeeeeeeee" + i));
//			}
			
			User henrikActivated = user3.setStatus(UserStatus.ACTIVE);
			User henrikUpdated = service.createOrUpdateUser(henrikActivated);
			
//			Testar om exception kastas när flera än 5 WorkItem tilldelas till User
//			for (int i = 0; i < 5; i++)
//			{
//				service.assignWorkItemToUser(henrikUpdated, new WorkItem("diska" + i).setStatus(WorkItemStatus.DONE));
//			}
			
			Issue foundIssue2 = service.findIssueById(12L);
			Issue updatedIssue2 = service.updateIssue(foundIssue2.setDescription("Katten sover"));
			System.out.println("updatedIssue2: " + updatedIssue2);
			System.out.println();
			
			WorkItem foundWorkItem1 = service.findWorkItemById(2L);
			service.createAndAddIssueToWorkItem(foundWorkItem1.setStatus(WorkItemStatus.DONE), new Issue("riskorn sitter fortfarande fast i skålarna"));
			
//			Testar om det går att ta bort WorkItem utan att påverka user
			WorkItem removedWorkItem = service.removeWorkItem(updatedIssue2.getWorkItem());
			System.out.println("removedWorkItem: " + removedWorkItem);
			System.out.println();
			
			User userWithRemovedWorkItem = removedWorkItem.getUser();
			System.out.println("userWithRemovedWorkItem: " + userWithRemovedWorkItem);
			User userFoundById = service.findUserById(userWithRemovedWorkItem.getId());
			System.out.println(userFoundById.getUsername() + " är fortfarande i databasen!");
			System.out.println();
			
			WorkItem workItemKattPromenad = service.findWorkItemById(7L);
			service.removeWorkItem(workItemKattPromenad);
		}
	}
}
