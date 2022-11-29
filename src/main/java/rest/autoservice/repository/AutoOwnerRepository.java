package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.AutoOwner;

@Repository
public interface AutoOwnerRepository extends JpaRepository<AutoOwner, Long> {
}
