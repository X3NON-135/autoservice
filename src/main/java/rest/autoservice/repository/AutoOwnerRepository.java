package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.autoservice.model.AutoOwner;

public interface AutoOwnerRepository extends JpaRepository<AutoOwner, Long> {
}
