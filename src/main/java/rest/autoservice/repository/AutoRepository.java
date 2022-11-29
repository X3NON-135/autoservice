package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.autoservice.model.Auto;
import rest.autoservice.model.AutoOwner;

public interface AutoRepository extends JpaRepository<Auto, Long> {
}
