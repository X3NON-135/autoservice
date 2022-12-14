package rest.autoservice.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
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
import rest.autoservice.dto.product.ProductRequestDto;
import rest.autoservice.model.Product;
import rest.autoservice.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    private Product product;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        product = new Product();
        product.setId(1L);
        product.setTitle("Car oil");
        product.setPrice(BigDecimal.valueOf(50));
    }

    @Test
    void shouldCreateNewProduct() {
        Mockito.when(productService.save(Mockito.any(Product.class))).thenReturn(product);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(product.getTitle(), product.getPrice()))
                .when()
                .post("/products")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("title", Matchers.equalTo("Car oil"))
                .body("price", Matchers.equalTo(50));
    }

    @Test
    void shouldUpdateProductFields() {
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setTitle("Timing belt");
        updatedProduct.setPrice(BigDecimal.valueOf(500));
        Mockito.when(productService.save(Mockito.any(Product.class))).thenReturn(updatedProduct);
        RestAssuredMockMvc.given()
                .queryParam("id", 1)
                .contentType(ContentType.JSON)
                .body(new ProductRequestDto(updatedProduct.getTitle(), updatedProduct.getPrice()))
                .when()
                .put("/products/{id}", 1)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("title", Matchers.equalTo("Timing belt"))
                .body("price", Matchers.equalTo(500));
    }
}
