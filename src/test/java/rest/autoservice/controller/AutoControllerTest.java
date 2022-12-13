package rest.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDate;
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
import rest.autoservice.dto.auto.AutoRequestDto;
import rest.autoservice.model.Auto;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Order;
import rest.autoservice.service.AutoOwnerService;
import rest.autoservice.service.AutoService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AutoControllerTest {
    @MockBean
    private AutoService autoService;
    @MockBean
    private AutoOwnerService autoOwnerService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private Auto auto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        auto = new Auto();
        auto.setId(1L);
        auto.setBrand("Bugatti");
        auto.setModel("Type 57SC");
        auto.setManufactureDate(LocalDate.of(1940, 6, 6));
        auto.setNumber("666Adams666");
        auto.setOwner(new AutoOwner(1L, "Asad", List.of(new Auto()), List.of(new Order())));
    }

    @Test
    void shouldCreateNewAuto() {
        Mockito.when(autoService.save(Mockito.any(Auto.class)))
                .thenReturn(auto);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new AutoRequestDto(auto.getBrand(), auto.getModel(), auto.getManufactureDate(),
                        auto.getNumber(), auto.getOwner().getId()))
                .when()
                .post("/autos")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("brand", Matchers.equalTo("Bugatti"))
                .body("model", Matchers.equalTo("Type 57SC"))
                .body("number", Matchers.equalTo("666Adams666"))
                .body("manufactureDate", Matchers.equalTo("1940-06-06"))
                .body("ownerId", Matchers.equalTo(1));
    }

    @Test
    void shouldUpdateAutoFields() {
        Auto updatedAuto = new Auto();
        updatedAuto.setId(1L);
        updatedAuto.setBrand("Mazda");
        updatedAuto.setModel("NA Miata");
        updatedAuto.setManufactureDate(LocalDate.of(1994, 6, 6));
        updatedAuto.setNumber("666Adams666");
        updatedAuto.setOwner(new AutoOwner(1L, "Asad", List.of(new Auto()), List.of(new Order())));
        Mockito.when(autoService.save(Mockito.any(Auto.class))).thenReturn(updatedAuto);
        RestAssuredMockMvc.given()
                .queryParam("id", auto.getId())
                .contentType(ContentType.JSON)
                .body(new AutoRequestDto(updatedAuto.getBrand(), updatedAuto.getModel(), updatedAuto.getManufactureDate(),
                        updatedAuto.getNumber(), updatedAuto.getOwner().getId()))
                .when()
                .put("/autos/{id}", 1)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("brand", Matchers.equalTo("Mazda"))
                .body("model", Matchers.equalTo("NA Miata"))
                .body("manufactureDate", Matchers.equalTo("1994-06-06"));
    }
}
