package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.repository.StudentRepository;
import ua.foxminded.university.repository.TeacherRepository;
import ua.foxminded.university.repository.UserRepository;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.UserService;

@SpringBootTest
@ContextConfiguration(initializers = {UserServiceImplTest.Initializer.class})
@Testcontainers
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    UserRegistrationRequest testStudent = new UserRegistrationRequest("TestStudent", "Doe", "rage@com", "1234", "1234", Status.STUDENT, RegistrationStatus.NEW);

    UserRegistrationRequest testTeacher = new UserRegistrationRequest("TestTeacher", "Doe", "rage@com", "1234", "1234", Status.TEACHER, RegistrationStatus.NEW);

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
    @Transactional
    void verifyUseMethodRegisterForStudent() throws ValidationException {
        userService.register(testStudent);

        assertEquals(testStudent.getFirstName(), userRepository.findAll().get(2).getFirstName());
        assertEquals(testStudent.getFirstName(), studentRepository.findAll().get(2).getFirstName());
    }

    @Test
    @Transactional
    void verifyUseMethodRegisterForTeacher() throws ValidationException {
        userService.register(testTeacher);

        assertEquals(testTeacher.getFirstName(), userRepository.findAll().get(2).getFirstName());
        assertEquals(testTeacher.getFirstName(), teacherRepository.findAll().get(0).getFirstName());
    }
}
