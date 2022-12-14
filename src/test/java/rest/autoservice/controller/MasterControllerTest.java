package rest.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
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
import rest.autoservice.dto.master.MasterRequestDto;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.service.MasterService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MasterControllerTest {
    @MockBean
    private MasterService masterService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private Master master;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        master = new Master();
        master.setId(1L);
        master.setFullName("Tyler Galpin");
        master.setFinishedOrders(Collections.emptyList());
    }

    @Test
    void shouldCreateNewMaster() {
        Mockito.when(masterService.save(Mockito.any(Master.class))).thenReturn(master);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new MasterRequestDto(master.getFullName(), Collections.emptyList()))
                .when()
                .post("/masters")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("fullName", Matchers.equalTo("Tyler Galpin"))
                .body("finishedOrdersIds", Matchers.equalTo(Collections.emptyList()));
    }

    @Test
    void calculateMastersSalaryById() {
        Order order = new Order();
        order.setId(1L);
        order.setTotalPrice(BigDecimal.valueOf(120));
        order.setStatus(Order.Status.PAID);
        master.setFinishedOrders(List.of(order));
        Mockito.when(masterService.getSalary(1L)).thenReturn(BigDecimal.valueOf(48));
        RestAssuredMockMvc.given()
                .queryParam("id", 1)
                .when()
                .get("/masters/{id}/salary", 1)
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("48"));
    }
}
