package rest.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
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
import rest.autoservice.dto.duties.DutyRequestDto;
import rest.autoservice.model.Auto;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.service.DutyService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class DutyControllerTest {
    @MockBean
    private DutyService dutyService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private Duty duty;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        duty = new Duty();
        duty.setId(1L);
        duty.setMaster(new Master(1L, "Tyler Gustin", Collections.emptyList()));
        duty.setOrder(new Order(1L, new Auto(), "diagnostics",
                null, null, null, null, null, BigDecimal.valueOf(100)));
        duty.setTypeOfDuty(Duty.TypeOfDuty.DIAGNOSTICS);
        duty.setPaymentStatus(Duty.PaymentStatus.UNPAID);
        duty.setPrice(BigDecimal.valueOf(500));
    }

    @Test
    void shouldCreateNewDuty() {
        Mockito.when(dutyService.save(Mockito.any(Duty.class))).thenReturn(duty);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new DutyRequestDto(duty.getMaster().getId(), duty.getOrder().getId(),
                        duty.getPrice(), duty.getTypeOfDuty().name()))
                .when()
                .post("/duties")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", Matchers.equalTo(1))
                .body("masterId", Matchers.equalTo(1))
                .body("orderId", Matchers.equalTo(1))
                .body("typeOfDuty", Matchers.equalTo("DIAGNOSTICS"));
    }

    @Test
    void shouldUpdateDutyFields() {
        Duty updatedDuty = new Duty();
        updatedDuty.setId(1L);
        updatedDuty.setMaster(new Master(2L, "Gus Ostin", Collections.emptyList()));
        updatedDuty.setOrder(new Order(2L, new Auto(), "change oil",
                null, null, null, null, null, BigDecimal.valueOf(100)));
        updatedDuty.setTypeOfDuty(Duty.TypeOfDuty.CHANGE_OIL);
        updatedDuty.setPaymentStatus(Duty.PaymentStatus.UNPAID);
        updatedDuty.setPrice(BigDecimal.valueOf(500));
        Mockito.when(dutyService.save(Mockito.any(Duty.class))).thenReturn(updatedDuty);
        RestAssuredMockMvc.given()
                .queryParam("id", 1)
                .contentType(ContentType.JSON)
                .body(new DutyRequestDto(updatedDuty.getMaster().getId(), updatedDuty.getOrder().getId(),
                        updatedDuty.getPrice(), updatedDuty.getTypeOfDuty().name()))
                .when()
                .put("/duties/{id}", 1)
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", Matchers.equalTo(1))
                .body("masterId", Matchers.equalTo(2))
                .body("orderId", Matchers.equalTo(2))
                .body("typeOfDuty", Matchers.equalTo("CHANGE_OIL"));
    }

    @Test
    void shouldFailOnWrongPaymentStatus() {
        Mockito.when(dutyService.findById(1L)).thenReturn(duty);
        RestAssuredMockMvc.given()
                .queryParam("id", 1)
                .queryParam("paymentStatus", "TEST")
                .when()
                .get("/duties/{id}/update-status", 1)
                .then()
                .statusCode(400);
    }
}
