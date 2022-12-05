package rest.autoservice.service.impl;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Order;
import rest.autoservice.model.Product;
import rest.autoservice.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {
    @Override
    public BigDecimal getTotalPrice(Order order, int discount) {
        return getDutyPrice(order, discount).add(getProductPrice(order, discount));
    }

    private BigDecimal getDutyPrice(Order order, int discount) {
        order.getDuties().forEach(duty -> duty.setPrice(
                duty.getPrice()
                        .subtract(duty.getPrice()
                                .multiply(BigDecimal.valueOf(discount * 0.01)))));
        return order.getDuties().stream()
                .map(Duty::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getProductPrice(Order order, int discount) {
        order.getProducts().forEach(product -> product.setPrice(
                product.getPrice()
                        .subtract(product.getPrice()
                                .multiply(BigDecimal.valueOf(discount * 0.02)))));
        return order.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
