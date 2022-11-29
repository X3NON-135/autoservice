package rest.autoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.autoservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
