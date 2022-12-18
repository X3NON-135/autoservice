package rest.autoservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Order;

@Repository
public interface AutoOwnerRepository extends JpaRepository<AutoOwner, Long> {
    @Query("SELECT ao FROM AutoOwner ao INNER JOIN ao.autos a where a.id = :id")
    AutoOwner getAutoOwnerByAutoId(Long id);

    @Query("SELECT o FROM Order o INNER JOIN o.auto a WHERE a.owner.id = :id")
    List<Order> getOrdersByAutoOwnerId(Long id);
}
