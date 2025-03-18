package fr.insee.formation.hibernate.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test-integration")
@TestInstance(Lifecycle.PER_CLASS)
@Testcontainers
public abstract class AbstractTestIntegration {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {

        if ((!postgresContainer.isRunning())) {
            postgresContainer.start();
        }
        registry.add("DB_URL", postgresContainer::getJdbcUrl);
        registry.add("DB_USERNAME", postgresContainer::getUsername);
        registry.add("DB_PASSWORD", postgresContainer::getPassword);

    }

    @AfterAll
    public void tearDown() {
        //Il est important de stopper le container après les tests
        //Surtout avec désactivation de Ryuk
        //cette instruction stop et détruit le conteneur à la fin des tests
        postgresContainer.stop();
    }
}
