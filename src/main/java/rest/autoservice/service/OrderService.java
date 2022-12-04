package rest.autoservice.service;

import java.math.BigDecimal;
import rest.autoservice.model.Order;

public interface OrderService {
    Order save(Order order);

    Order findById(Long id);

    BigDecimal calculatePriceForOrder(Long id, int discount);
}
