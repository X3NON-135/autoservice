package rest.autoservice.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Order;
import rest.autoservice.model.Product;
import rest.autoservice.repository.OrderRepository;
import rest.autoservice.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private static final double PRICE_FOR_DIAGNOSTICS = 500;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find order by id=" + id));
    }

    @Override
    public Order calculatePriceForOrder(Long id) {
        Order order = findById(id);
        List<Duty> duties = order.getDuties();
        List<Product> products = order.getProducts();
        double sumForDuties = duties.size() == 1 ? PRICE_FOR_DIAGNOSTICS :
                duties.stream().mapToDouble(Duty::getPrice).sum();
        double sumForProducts = products.stream().mapToDouble(Product::getPrice).sum();
        order.setTotalPrice((sumForDuties * duties.size())
                + sumForProducts * products.size());
        return order;
    }
}
