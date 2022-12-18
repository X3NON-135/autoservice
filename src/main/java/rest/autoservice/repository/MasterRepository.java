package rest.autoservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    @Query("SELECT o FROM Order o "
            + "JOIN FETCH o.duties d "
            + "JOIN FETCH d.master m "
            + " WHERE o.status = 'PAID' AND m.id = :id")
    List<Order> getFinishedOrdersByMasterId(Long id);
}
