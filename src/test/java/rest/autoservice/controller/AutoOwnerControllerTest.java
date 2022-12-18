package rest.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
import rest.autoservice.dto.owner.AutoOwnerRequestDto;
import rest.autoservice.model.Auto;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Order;
import rest.autoservice.model.Product;
import rest.autoservice.service.AutoOwnerService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AutoOwnerControllerTest {
    @MockBean
    private AutoOwnerService autoOwnerService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private AutoOwner autoOwner;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        autoOwner = new AutoOwner();
        autoOwner.setId(1L);
        autoOwner.setFullName("Wednesday Adams");
        autoOwner.setAutos(Collections.emptyList());
        autoOwner.setOrders(Collections.emptyList());
    }

    @Test
    void shouldCreateNewAutoOwner() {
        Mockito.when(autoOwnerService.save(Mockito.any(AutoOwner.class)))
                .thenReturn(autoOwner);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new AutoOwnerRequestDto(autoOwner.getFullName(),
                        Collections.emptyList(), Collections.emptyList()))
                .when()
                .post("/owners")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("fullName", Matchers.equalTo("Wednesday Adams"))
                .body("autoIds", Matchers.equalTo(Collections.emptyList()))
                .body("orderIds", Matchers.equalTo(Collections.emptyList()));
    }

    @Test
    void shouldUpdateAutoOwnerFields() {
        AutoOwner updatedOwner = new AutoOwner();
        updatedOwner.setId(1L);
        updatedOwner.setFullName("Item");
        updatedOwner.setAutos(Collections.emptyList());
        updatedOwner.setOrders(Collections.emptyList());
        Mockito.when(autoOwnerService.save(Mockito.any(AutoOwner.class)))
                .thenReturn(updatedOwner);
        RestAssuredMockMvc.given()
                .queryParam("id", autoOwner.getId())
                .contentType(ContentType.JSON)
                .body(new AutoOwnerRequestDto(updatedOwner.getFullName(),
                        Collections.emptyList(), Collections.emptyList()))
                .when()
                .put("/owners/{id}", 1)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("fullName", Matchers.equalTo("Item"))
                .body("autoIds", Matchers.equalTo(Collections.emptyList()))
                .body("orderIds", Matchers.equalTo(Collections.emptyList()));
    }

    @Test
    void shouldGetAutoOwnersOrders() {
        Order newOrder = new Order(66L, new Auto(), "test",
                LocalDateTime.of(2022, 2, 1, 12, 0),
                LocalDateTime.of(2022, 2, 1, 13, 0),
                List.of(new Duty()),
                List.of(new Product()), Order.Status.ACCEPTED, BigDecimal.valueOf(100));
        autoOwner.setOrders(List.of(newOrder));
        Mockito.when(autoOwnerService.getOrdersByAutoOwnerId(1L)).thenReturn(List.of(newOrder));
        RestAssuredMockMvc.given()
                .when()
                .get("/owners/{id}/orders", 1)
                .then()
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(66));
    }
}
