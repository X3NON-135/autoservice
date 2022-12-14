package rest.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import rest.autoservice.dto.order.OrderRequestDto;
import rest.autoservice.model.Auto;
import rest.autoservice.model.Order;
import rest.autoservice.service.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private Order order;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        order = new Order();
        order.setId(1L);
        order.setAuto(new Auto(1L, "Mercedes", "AMG", null, "OO4044AO", null));
        order.setDescription("diagnostics");
        order.setAcceptanceDate(LocalDateTime.of(2022, 12, 13, 11, 20));
        order.setFinishedDate(LocalDateTime.of(2022, 12, 13, 14, 20));
        order.setStatus(Order.Status.ACCEPTED);
        order.setDuties(Collections.emptyList());
        order.setProducts(Collections.emptyList());
        order.setTotalPrice(BigDecimal.valueOf(404));
    }

    @Test
    void shouldCreateNewOrder() {
        Mockito.when(orderService.save(Mockito.any(Order.class))).thenReturn(order);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OrderRequestDto(order.getAuto().getId(), order.getDescription(),
                        order.getAcceptanceDate(), order.getFinishedDate(),
                        order.getStatus().name(), order.getTotalPrice(),
                        Collections.emptyList(), Collections.emptyList()))
                .when()
                .post("/orders")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("description", Matchers.equalTo("diagnostics"))
                .body("acceptanceDate", Matchers.equalTo("2022-12-13T11:20:00"))
                .body("finishedDate", Matchers.equalTo("2022-12-13T14:20:00"))
                .body("status", Matchers.equalTo("ACCEPTED"))
                .body("totalPrice", Matchers.equalTo(404));
    }

    @Test
    void calculatePriceForOrder() {
        Mockito.when(orderService.calculatePriceForOrder(1L))
                .thenReturn(order);
        RestAssuredMockMvc.given()
                .queryParam("id", 1)
                .when()
                .get("/orders/{id}/calculate-price", 1)
                .then()
                .statusCode(200)
                .body("totalPrice", Matchers.equalTo(404));
    }
}
