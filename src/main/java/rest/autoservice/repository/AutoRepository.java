package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.Auto;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
}
