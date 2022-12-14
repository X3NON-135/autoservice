package rest.autoservice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rest.autoservice.model.Auto;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.model.Product;
import rest.autoservice.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static Order order;
    private static Product product;
    private static Duty duty;
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;

    @BeforeAll
    static void beforeAll() {
        duty = new Duty();
        duty.setMaster(new Master());
        duty.setOrder(order);
        duty.setTypeOfDuty(Duty.TypeOfDuty.DIAGNOSTICS);
        duty.setPrice(BigDecimal.valueOf(500));

        product = new Product();
        product.setId(1L);
        product.setTitle("car oil");
        product.setPrice(BigDecimal.valueOf(200));

        order = new Order();
        order.setId(1L);
        order.setAuto(new Auto());
        order.setDescription("some fix");
        order.setAcceptanceDate(LocalDateTime.now());
        order.setFinishedDate(LocalDateTime.now());
        order.setStatus(Order.Status.ACCEPTED);
        order.setDuties(List.of(duty));
        order.setProducts(new ArrayList<>());
        order.setTotalPrice(BigDecimal.valueOf(0));
    }

    @Test
    void calculatePriceForOrder_Ok() {
        order.getProducts().add(product);
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        BigDecimal actualPrice = orderService.calculatePriceForOrder(1L).getTotalPrice();
        // expected price = (500 + 200) - [(500 + 200) * 0.02] = 686
        BigDecimal expectedPrice = BigDecimal.valueOf(686);
        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void addProductToOrder_NotNull_Ok() {
        order.getProducts().add(product);
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Product newProduct = new Product();
        newProduct.setId(2L);
        newProduct.setTitle("tiring belt");
        newProduct.setPrice(BigDecimal.valueOf(353));
        Order actual = orderService.addProductToOrder(1L, newProduct);
        Assertions.assertNotNull(actual.getProducts().get(1));
        Assertions.assertEquals(order.getProducts().size(), actual.getProducts().size());
    }
}
