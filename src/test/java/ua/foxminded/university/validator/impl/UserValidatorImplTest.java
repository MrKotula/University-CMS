package ua.foxminded.university.validator.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.service.dto.updateData.UserAccountUpdateRequest;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentAccountService;
import java.util.HashSet;

@SpringBootTest
@ContextConfiguration(initializers = {UserValidatorImplTest.Initializer.class})
@Testcontainers
class UserValidatorImplTest {

    @Autowired
    StudentAccountService studentAccountService;

    @Autowired
    UserAccountService userAccountService;

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
    void shouldReturnValidationExceptionWhenFirstNameIsLonger() throws ValidationException {
        String expectedMessage = "First name or last name is has more 16 symbols!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.register(new UserRegistrationRequest("JohnJohnJohnJohnF", "Doe", "testemail@ukr.net",
                "12345678", "12345678", RegistrationStatus.NEW, new HashSet<>())));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenLastNameIsLonger() throws ValidationException {
        String expectedMessage = "First name or last name is has more 16 symbols!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.register(new UserRegistrationRequest("John", "DoeDOEDoeDOEDoeDOE", "testemail@ukr.net",
                "12345678", "12345678", RegistrationStatus.NEW, new HashSet<>())));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenNotContainSpecialSymbol() throws ValidationException {
        String expectedMessage = "Email is not correct!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.register(new UserRegistrationRequest("John", "Doe", "testemailukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>())));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenDataContainSpecialCharacters() throws ValidationException {
        String expectedMessage = "Data cannot contain special characters!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.register(new UserRegistrationRequest("Joh@n", "!Doe", "testemail@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>())));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenPasswordDoesNotMatch() throws ValidationException {
        String expectedMessage = "Passwords does not match!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.register(new UserRegistrationRequest("John", "Doe", "testemail@ukr.net", "12345678",
                "123456", RegistrationStatus.NEW, new HashSet<>())));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenLoginIsAlreadyExists() throws ValidationException {
        String expectedMessage = "This login already exists!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.register(new UserRegistrationRequest("John", "Doe", "dis@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>())));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUserIdIsNotExists() throws ValidationException {
        String expectedMessage = "This userId is not exists!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.addStudentCourse("sad", "1d95bc79-a549-4d2c-aeb5-3f929aee0096"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUseUpdateDataFirstNameIsLonger() throws ValidationException {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "testTestTestTestT", "test", "asdc@om.ua", "1234", "1234");
        String expectedMessage = "First name or last name is has more 16 symbols!";
        Exception exception = assertThrows(ValidationException.class, () -> userAccountService.updateUserData(userAccountUpdateRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUseUpdateDataLastNameIsLonger() throws ValidationException {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "test", "testTestTestTestT", "assdc@om.ua", "1234", "1234");
        String expectedMessage = "First name or last name is has more 16 symbols!";
        Exception exception = assertThrows(ValidationException.class, () -> userAccountService.updateUserData(userAccountUpdateRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUseUpdateDataPasswordDoesNotMatch() throws ValidationException {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "test", "test", "asdcdd@om.ua", "12384", "1234");
        String expectedMessage = "Passwords does not match!";
        Exception exception = assertThrows(ValidationException.class, () -> userAccountService.updateUserData(userAccountUpdateRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUseUpdateData() throws ValidationException {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "test", "test", "asdc@om.ua", "1234", "1234");

        assertDoesNotThrow(() -> userAccountService.updateUserData(userAccountUpdateRequest));
    }
}
