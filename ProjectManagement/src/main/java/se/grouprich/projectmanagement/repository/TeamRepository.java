package se.grouprich.projectmanagement.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.grouprich.projectmanagement.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>, NumberSetter<Team, TeamRepository>
{
	@Query("SELECT t FROM #{#entityName} t order by t.controlNumber DESC")
	Slice<Team> findAllOrderedByControlNumberDESC(Pageable pageable);
}