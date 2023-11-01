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
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.CourseService;

@SpringBootTest
@ContextConfiguration(initializers = {CourseValidatorImplTest.Initializer.class})
@Testcontainers
class CourseValidatorImplTest {

    @Autowired
    CourseService courseService;

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
    void shouldReturnValidationExceptionWhenCourseNameIsLonger() throws ValidationException {
        String expectedMessage = "Course name is has more 24 symbols!";
        Exception exception = assertThrows(ValidationException.class, () -> courseService.register("TestTestTestTestTestTestT", "test"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenCourseDescriptionIsLonger() throws ValidationException {
        String expectedMessage = "Course description is has more 36 symbols!";
        Exception exception = assertThrows(ValidationException.class, () -> courseService.register("Test", "TestTestTestTestTestTestTestTestTestT"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenCourseIncludeSpecialCharacters() throws ValidationException {
        String expectedMessage = "Data cannot contain special characters!";
        Exception exception = assertThrows(ValidationException.class, () -> courseService.register("Tes@t", "TestTes@tTestTestTestTestTestTestTestT"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenCourseIdIsNotExists() throws ValidationException {
        String expectedMessage = "This courseId is not exists!";
        StudentAccountResponse studentAccountResponse = StudentAccountResponse.builder().userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").build();

        Exception exception = Assert.assertThrows(ValidationException.class, () -> studentAccountService.addStudentCourse(studentAccountResponse, "1d95bc79-a549-4d2c-aeb5-3f929aee0446"));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
