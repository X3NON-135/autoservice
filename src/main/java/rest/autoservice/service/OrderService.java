package rest.autoservice.service;

import rest.autoservice.model.Order;

public interface OrderService {
    Order save(Order order);

    Order findById(Long id);
}
