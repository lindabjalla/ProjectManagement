package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.model.Team;
import se.grouprich.projectmanagement.model.User;
import se.grouprich.projectmanagement.model.WorkItem;
import se.grouprich.projectmanagement.status.WorkItemStatus;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long>
{
	@Transactional
	List<WorkItem> removeById(Long id);
	
	List<WorkItem> findByStatus(WorkItemStatus status);
	
	List<WorkItem> findByDescriptionContaining(String keyword);
	
	@Query("SELECT w FROM #{#entityName} w WHERE w.user.team = ?1")
	List<WorkItem> findByTeam(Team team);
	
	List<WorkItem> findByUser(User user);
	
	@Query("SELECT w FROM #{#entityName} w order by w.controlNumber DESC")
	Slice<WorkItem> findAllOrderedByControlNumberDESC(Pageable pageable);
}
