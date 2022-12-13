package rest.autoservice.service;

import rest.autoservice.model.Order;
import rest.autoservice.model.Product;

public interface OrderService {
    Order save(Order order);

    Order findById(Long id);

    Order updateStatus(Long id, String status);

    Order calculatePriceForOrder(Long id);

    Order addProductToOrder(Long orderId, Product product);
}
