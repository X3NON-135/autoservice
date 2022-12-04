package rest.autoservice.service;

import java.math.BigDecimal;
import rest.autoservice.model.Order;

public interface PriceService {
    BigDecimal getTotalPrice(Order order, int discount);
}
