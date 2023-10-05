package ua.foxminded.university.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.GroupService;

@SpringBootTest
@ContextConfiguration(initializers = {GroupValidatorImplTest.Initializer.class})
@Testcontainers
class GroupValidatorImplTest {

    @Autowired
    GroupService groupService;

    @Autowired
    StudentAccountService studentAccountService;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("integration-tests-db").withUsername("sa").withPassword("sa");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void shouldReturnValidationExceptionWhenGroupNameCannotSpecialCharacter() throws ValidationException {
        String expectedMessage = "Group name cannot special format for group!";
        Exception exception = assertThrows(ValidationException.class, () -> groupService.register("test"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenGroupIdIsNotExists() throws ValidationException {
        String expectedMessage = "This groupId is not exists!";
        Exception exception = Assert.assertThrows(ValidationException.class, () -> studentAccountService.changeGroup("sad", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
