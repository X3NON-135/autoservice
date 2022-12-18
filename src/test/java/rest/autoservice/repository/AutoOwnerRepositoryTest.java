package rest.autoservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import rest.autoservice.model.AutoOwner;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AutoOwnerRepositoryTest {
    /*TO START THIS TEST MAKE SURE THAT ***DOCKER DESKTOP*** IS LAUNCHED*/
    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("springboot")
            .withPassword("springboot")
            .withUsername("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private AutoOwnerRepository autoOwnerRepository;


    @Test
    @Sql({"/scripts/add-owner.sql", "/scripts/add-auto.sql"})
    void getAutoOwnerFullName_ByAutoId_Ok() {
        AutoOwner actual = autoOwnerRepository.getAutoOwnerByAutoId(1L);
        Assertions.assertNotNull(actual);
        Assertions.assertNotEquals("Bugatti", actual.getAutos().get(0).getBrand());
        Assertions.assertEquals("Wednesday Adams", actual.getFullName());
    }
}
