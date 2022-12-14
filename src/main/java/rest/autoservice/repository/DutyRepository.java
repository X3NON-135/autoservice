package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.Duty;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {
}
