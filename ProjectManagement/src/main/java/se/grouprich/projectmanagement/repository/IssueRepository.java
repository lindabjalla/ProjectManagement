package se.grouprich.projectmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import se.grouprich.projectmanagement.model.Issue;
import se.grouprich.projectmanagement.model.WorkItem;

public interface IssueRepository extends CrudRepository<Issue, Long>, NumberSetter<Issue, IssueRepository>
{
	@Query("SELECT i.workItem FROM #{#entityName} i")
	List<WorkItem> findWorkItemsHavingIssue();
	
	@Transactional
	List<Issue> removeByWorkItem(WorkItem workItem);
	
	@Query("SELECT i FROM #{#entityName} i order by i.controlNumber DESC")
	Slice<Issue> findAllOrderedByControlNumberDESC(Pageable pageable);
}
