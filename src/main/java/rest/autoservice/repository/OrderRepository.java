package rest.autoservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.autoservice.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders o JOIN orders_products op ON o.id = op.order_id "
            + " WHERE o.id = :id", nativeQuery = true)
    Optional<Order> findByOrderId(Long id);
}
