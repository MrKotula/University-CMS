package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
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
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentAccountService;

@SpringBootTest
@ContextConfiguration(initializers = {StudentAccountServiceImplTest.Initializer.class})
@Testcontainers
class StudentAccountServiceImplTest {

    @Autowired
    StudentAccountService studentAccountService;

    @Autowired
    StudentAccountRepository studentAccountRepository;

    StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", null, null, null,
           RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

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
    void verifyUseMethodRegister() throws ValidationException {
        studentAccountService.register(new UserRegistrationRequest("John", "Doe", "testemail@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>()));

        assertEquals(Optional.of(testStudentAccount), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateEmail() throws ValidationException {
        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net", null, null,
                RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");
        studentAccountService.updateEmail(testStudentAccount);

        assertEquals(Optional.of(testStudentAccount), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void verifyUseMethodUpdatePassword() {
        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net",
                "1234", "1234", RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");
        studentAccountService.updatePassword(testStudentAccount);

        assertEquals(Optional.of(testStudentAccount), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void verifyUseMethodUChangeGroup() {
        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", null, "12345678", "12345678",
                RegistrationStatus.REGISTERED, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce5325");
        studentAccountService.changeGroup("3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

        assertEquals(Optional.of(testStudentAccount), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void shouldReturnListOfStudentsWhenUseGetStudentsWithCourseName() {
        List<StudentAccount> testListStudentAccount = Arrays.asList(new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", null, null, null,
                RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2"));

        assertEquals(testListStudentAccount, studentAccountService.findByCourseName("math"));
    }

    @Test
    @Transactional
    void shouldReturnListOfStudentsWhenUseRemoveStudentFromCourse() {
        List<StudentAccount> emptyList = Collections.emptyList();
        studentAccountService.removeStudentFromCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1",
                "1d95bc79-a549-4d2c-aeb5-3f929aee0f22");

        assertEquals(emptyList, studentAccountService.findByCourseName("math"));
    }

    @Test
    @Transactional
    void verifyUseMethodWhenUseInsertSaveAndAddStudentCourse() {
        List<StudentAccount> testListStudentAccount = Arrays.asList(testStudentAccount);
        studentAccountRepository.save(new StudentAccount("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "John", "Doe", "asd@sa", "123140", "123140", RegistrationStatus.NEW, new HashSet<>()));
        studentAccountService.addStudentCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        assertEquals(testListStudentAccount, studentAccountService.findByCourseName("drawing"));
    }

    @Test
    @Transactional
    void verifyUseMethodWhenUseDeleteById() {
        List<StudentAccount> testListStudentAccount = Arrays.asList(new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", null, null, null,
                 RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce5325"));
        studentAccountService.deleteById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

        assertEquals(testListStudentAccount, studentAccountRepository.findAll());
    }
}
