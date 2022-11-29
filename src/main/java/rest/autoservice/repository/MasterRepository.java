package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.autoservice.model.Master;

public interface MasterRepository extends JpaRepository<Master, Long> {
}
