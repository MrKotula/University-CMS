package ua.foxminded.university.validator.impl;

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
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.Arrays;
import java.util.HashSet;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = {StudentValidatorImplTest.Initializer.class})
@Testcontainers
class StudentValidatorImplTest {

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
    void shouldReturnValidationExceptionWhenMoreMaxAvailableCoursesTest() throws ValidationException {
        String expectedMessage = "You have a max available courses!";

        Course testCourseChemistry = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5324", "Chemistry", "course of Chemistry", 15);
        Course testCoursePhysics = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee6589", "Physics", "course of Physics", 15);
        Course testCoursePhilosophy = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee8999", "Philosophy", "course of Philosophy", 30);
        Course testCourseDrawing = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0096", "Drawing", "course of Drawing", 10);
        Course testCourseLiterature = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1222", "Literature", "course of Literature", 10);
        Course testCourseEnglish = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee7658", "English", "course of English", 30);
        Course testCourseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course testCourseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(testCourseChemistry, testCoursePhysics, testCoursePhilosophy,
                testCourseDrawing, testCourseLiterature, testCourseEnglish, testCourseBiology, testCourseMath));

        StudentAccountResponse studentAccountResponse = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.addStudentCourse(studentAccountResponse, "1d95bc79-a549-4d2c-aeb5-3f929aee0887"));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
