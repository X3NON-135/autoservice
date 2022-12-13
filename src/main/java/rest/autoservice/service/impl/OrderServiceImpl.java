package rest.autoservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

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
    public Order updateStatus(Long id, String status) {
        Order order = findById(id);
        order.setId(id);
        order.setStatus(Order.Status.valueOf(status.toUpperCase()));
        if (status.toUpperCase().equals(Order.Status.SUCCESSFUL_DONE.toString())
                || status.toUpperCase().equals(Order.Status.UNSUCCESSFUL_DONE.toString())) {
            order.setFinishedDate(LocalDateTime.now());
        }
        return order;
    }

    @Override
    public Order calculatePriceForOrder(Long id) {
        Order order = findById(id);
        List<Duty> duties = order.getDuties();
        List<Product> products = order.getProducts();
        double dutiesPrice = duties.size() == 1 ? PRICE_FOR_DIAGNOSTICS :
                duties.stream().mapToDouble(Duty::getPrice).sum();
        double productsPrice = products.stream().mapToDouble(Product::getPrice).sum();
        double priceWithoutDiscount = dutiesPrice + productsPrice;
        double discount = priceWithoutDiscount * ((duties.size() + products.size()) / 100.0);
        order.setTotalPrice(priceWithoutDiscount - discount);
        return order;
    }

    @Override
    public Order addProductToOrder(Long orderId, Product product) {
        Order order = findById(orderId);
        List<Product> products = order.getProducts();
        products.add(product);
        order.setProducts(products);
        return order;
    }
}
