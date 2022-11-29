package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.autoservice.model.Duty;

public interface DutyRepository extends JpaRepository<Duty, Long> {
}
