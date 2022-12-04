package rest.autoservice.service.impl;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Order;
import rest.autoservice.repository.OrderRepository;
import rest.autoservice.service.OrderService;
import rest.autoservice.service.PriceService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final PriceService priceService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderService orderService,
                            PriceService priceService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.priceService = priceService;
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
    public BigDecimal calculatePriceForOrder(Long id, int discount) {
        Order order = orderService.findById(id);
        BigDecimal totalPrice = priceService.getTotalPrice(order, discount);
        order.setTotalPrice(totalPrice);
        save(order);
        return totalPrice;
    }
}
