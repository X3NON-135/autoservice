package rest.autoservice.service.impl;

import org.springframework.stereotype.Component;
import rest.autoservice.model.Order;
import rest.autoservice.repository.OrderRepository;
import rest.autoservice.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find order by id=" + id));
    }
}
