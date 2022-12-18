package rest.autoservice.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Order;
import rest.autoservice.model.Product;
import rest.autoservice.repository.OrderRepository;
import rest.autoservice.service.OrderService;
import rest.autoservice.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {
    private static final BigDecimal PRICE_FOR_DIAGNOSTICS = BigDecimal.valueOf(500);
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
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
        return save(order);
    }

    @Override
    public Order calculatePriceForOrder(Long id) {
        Order order = orderRepository.findByOrderId(id).orElseThrow(() ->
                new RuntimeException("Can't find order by id=" + id));
        List<Duty> duties = order.getDuties();
        List<Product> products = order.getProducts();
        BigDecimal dutiesPrice = duties.size() == 1 ? PRICE_FOR_DIAGNOSTICS :
                duties.stream().map(Duty::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal productsPrice = products.stream().map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal priceWithoutDiscount = dutiesPrice.add(productsPrice);
        BigDecimal discount = priceWithoutDiscount.multiply(
                BigDecimal.valueOf((duties.size() + products.size()) / 100.0))
                .round(new MathContext(4));
        order.setTotalPrice(priceWithoutDiscount.subtract(discount));
        save(order);
        return order;
    }

    @Override
    public Order addProductToOrder(Long orderId, Product product) {
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(() ->
                new RuntimeException("Can't find order by id=" + orderId));
        order.getProducts().add(product);
        productService.save(product);
        save(order);
        return order;
    }
}
