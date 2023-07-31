package ua.foxminded.university.validator.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentAccountService;
import java.util.HashSet;

@SpringBootTest
class UserValidatorImplTest {

    @Autowired
    StudentAccountService studentAccountService;

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
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.register(new UserRegistrationRequest("John", "Doe", "dors@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>())));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUserIdIsNotExists() throws ValidationException {
        String expectedMessage = "This userId is not exists!";
        Exception exception = assertThrows(ValidationException.class, () -> studentAccountService.addStudentCourse("sad", "1d95bc79-a549-4d2c-aeb5-3f929aee0096"));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
