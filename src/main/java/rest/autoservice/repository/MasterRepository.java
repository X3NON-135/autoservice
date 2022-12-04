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
            + " INNER JOIN FETCH o.duties d "
            + " INNER JOIN FETCH d.master m "
            + " WHERE m.id = :id")
    List<Order> getCompletedOrdersById(Long id);

    @Query("SELECT m FROM Master m WHERE m.id in "
            + " (SELECT o FROM Order o "
            + " INNER JOIN FETCH o.duties d "
            + " INNER JOIN FETCH d.master dMas"
            + " WHERE o.id = :id)")
    List<Master> getAllMastersByOrderId(Long id);
}
