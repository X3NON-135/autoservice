package rest.autoservice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Order;
import rest.autoservice.repository.AutoOwnerRepository;

@ExtendWith(MockitoExtension.class)
class AutoOwnerServiceImplTest {
    private static AutoOwner expected;
    @InjectMocks
    private AutoOwnerServiceImpl autoOwnerService;
    @Mock
    private AutoOwnerRepository autoOwnerRepository;

    @BeforeAll
    public static void setUp() {
        expected = new AutoOwner();
        expected.setId(1L);
        expected.setFullName("Wednesday Adams");
        expected.setAutos(new ArrayList<>());
        expected.setOrders(new ArrayList<>());
    }

    @Test
    void addAutoToAutoOwner_NotNull_Ok() {
        Mockito.when(autoOwnerRepository.findById(1L)).thenReturn(Optional.of(expected));
        Auto auto = new Auto();
        auto.setId(1L);
        auto.setBrand("Bugatti");
        auto.setModel("Type 57SC");
        auto.setNumber("666Adams666");
        auto.setManufactureDate(LocalDate.of(1940, 6, 6));
        auto.setOwner(expected);
        AutoOwner actual = autoOwnerService.addAuto(1L, auto);
        expected.getAutos().add(auto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getAutos().get(0).getBrand(), actual.getAutos().get(0).getBrand());
        Assertions.assertEquals(expected.getAutos().size(), actual.getAutos().size());
    }

    @Test
    void addOrderToAutoOwner_NotNull_Ok() {
        Mockito.when(autoOwnerRepository.findById(1L)).thenReturn(Optional.of(expected));
        Order order = new Order();
        order.setId(1L);
        order.setAuto(new Auto());
        order.setDescription("some fix");
        order.setAcceptanceDate(LocalDateTime.now());
        order.setFinishedDate(LocalDateTime.now());
        order.setStatus(Order.Status.ACCEPTED);
        order.setDuties(new ArrayList<>());
        order.setProducts(new ArrayList<>());
        order.setTotalPrice(BigDecimal.valueOf(230));
        AutoOwner actual = autoOwnerService.addOrder(1L, order);
        expected.getOrders().add(order);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getOrders().size(), actual.getOrders().size());
    }
}
