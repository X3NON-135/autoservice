package rest.autoservice.service;

import rest.autoservice.model.Order;

public interface OrderService {
    Order findById(Long id);
}
