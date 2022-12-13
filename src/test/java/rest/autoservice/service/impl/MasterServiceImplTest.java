package rest.autoservice.service.impl;

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
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.repository.MasterRepository;

@ExtendWith(MockitoExtension.class)
class MasterServiceImplTest {
    private static Order order;
    private static Master master;
    private static final double PERCENT_FROM_DUTY_PRICE = 0.4;
    @InjectMocks
    private MasterServiceImpl masterService;
    @Mock
    private MasterRepository masterRepository;

    @BeforeAll
    public static void setUp() {
        order = new Order();
        order.setId(1L);
        order.setAuto(new Auto());
        order.setDescription("some fix");
        order.setAcceptanceDate(LocalDateTime.now());
        order.setFinishedDate(LocalDateTime.now());
        order.setStatus(Order.Status.ACCEPTED);
        order.setDuties(new ArrayList<>());
        order.setProducts(new ArrayList<>());
        order.setTotalPrice(230);

        master = new Master();
        master.setId(1L);
        master.setFullName("Tyler Galpin");
        master.setFinishedOrders(List.of(order));
    }

    @Test
    void getSalary_ByFinishedOrder_Ok() {
        Mockito.when(masterRepository.findById(1L)).thenReturn(Optional.of(master));
        List<Order> actual = masterService.getFinishedOrdersById(1L);
        List<Order> expected = master.getFinishedOrders();
        double expectedTotalPrice = expected.get(0).getTotalPrice() * PERCENT_FROM_DUTY_PRICE;
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedTotalPrice, masterService.getSalary(1L));
        Assertions.assertEquals(Order.Status.PAID, actual.get(0).getStatus());
    }
}
