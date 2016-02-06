package se.grouprich.projectmanagement;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import se.grouprich.projectmanagement.exception.TeamException;
import se.grouprich.projectmanagement.exception.WorkItemException;
import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.service.IssueService;
import se.grouprich.projectmanagement.service.TeamService;
import se.grouprich.projectmanagement.service.UserService;
import se.grouprich.projectmanagement.service.WorkItemService;
import se.grouprich.projectmanagement.status.WorkItemStatus;

public final class Main2
{
	public static void main(String[] args) throws TeamException, WorkItemException
	{
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.scan("se.grouprich.projectmanagement");
			context.refresh();
			UserService userService = context.getBean(UserService.class);
			TeamService teamService = context.getBean(TeamService.class);
			WorkItemService workItemService = context.getBean(WorkItemService.class);
			IssueService issueService = context.getBean(IssueService.class);

			System.out.println();
			User user1 = userService.createOrUpdate(new User("IzumiJapan", "12345", "Izumi", "Suzuki", "101"));
			User user2 = userService.createOrUpdate(new User("HaydeePeru", "123", "Haydee", "Arbieto de Alvarado", "102"));
			User user3 = userService.createOrUpdate(new User("CristianChile", "345", "Cristian", "Gonzales", "103"));

			Team createdTeam = teamService.createOrUpdate(new Team("Team Forest"));
			User user4 = userService.createOrUpdate(new User("RodionEstland", "1234", "Rodion", "Estland", "104"));
			WorkItem createdWorkItem = workItemService.createOrUpdate(new WorkItem("Fånga en fågel").setStatus(WorkItemStatus.STARTED));

			System.out.println("createdWorkItem: " + createdWorkItem);
			System.out.println();

			User updatedUser = userService.createOrUpdate(user2.setUsername("Son Goku Gahahaha"));
			workItemService.assignWorkItemToUser(updatedUser, createdWorkItem);

			userService.inactivateUser(updatedUser);

			issueService.createAndAddToWorkItem(createdWorkItem.setStatus(WorkItemStatus.DONE), new Issue("fågel finns inte"));

			teamService.inactivateTeam(createdTeam);

			Team teamA = teamService.createOrUpdate(new Team("TeamA"));

			Iterable<Team> allTeams = teamService.findAll();
			System.out.println(allTeams);

			teamService.addUserToTeam(createdTeam, new User("100KopparKaffe", "44444", "Kaffe", "Sugen", "105"));

			User userFetchedByUserNumber101 = userService.fetchUserByUserNumber("101");
			System.out.println("User fetch by userNumber 101: " + userFetchedByUserNumber101);

			User userSearchByExactMatching = userService.searchUserByFirstNameAndLastNameAndUsername("Haydee", "Arbieto de Alvarado", "Son Goku Gahahaha");
			System.out.println("User search by exact matching: " + userSearchByExactMatching);

			List<User> userSearchBySomeName = userService.searchUserByFirstNameOrLastNameOrUsername(" ", "Suzuki", "RodionEstland");
			System.out.println("Search users Susuki och Rodion: " + userSearchBySomeName);

			User user1AddedToTeamA = teamService.addUserToTeam(teamA, user1);
			System.out.println("User1 joined to teamA: " + user1AddedToTeamA);
		}
	}
}
