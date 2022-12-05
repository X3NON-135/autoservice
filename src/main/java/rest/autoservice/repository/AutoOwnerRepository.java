package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.AutoOwner;

@Repository
public interface AutoOwnerRepository extends JpaRepository<AutoOwner, Long> {
    @Query("SELECT ao FROM AutoOwner ao INNER JOIN ao.autos a where a.id = :id")
    AutoOwner getAutoOwnerByAutoId(Long id);
}
