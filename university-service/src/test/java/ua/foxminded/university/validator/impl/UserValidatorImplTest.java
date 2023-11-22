package ua.foxminded.university.validator.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserValidatorImplTest {

    @InjectMocks
    private UserValidatorImpl userValidator;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Test
    void shouldReturnValidationExceptionWhenFirstNameIsLonger() throws ValidationException {
        String expectedMessage = "First name or last name is has more 16 symbols!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("JohnJohnJohnJohnF", "Doe", "testemail@ukr.net",
                "12345678", "12345678", RegistrationStatus.NEW, new HashSet<>());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenLastNameIsLonger() throws ValidationException {
        String expectedMessage = "First name or last name is has more 16 symbols!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "DoeDOEDoeDOEDoeDOE", "testemail@ukr.net",
                "12345678", "12345678", RegistrationStatus.NEW, new HashSet<>());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUpdateRequestFirstNameIsLonger() throws ValidationException {
        String expectedMessage = "First name or last name is has more 16 symbols!";

        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "JohnJohnJohnJohnG", "Doe",
                "testema@ukr.net", "12345678", "12345678", new HashSet<>(), RegistrationStatus.NEW);

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userAccountUpdateRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUpdateRequestLastNameIsLonger() throws ValidationException {
        String expectedMessage = "First name or last name is has more 16 symbols!";

        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "DoeDoeDoeDoeDoeDoeDoeDoe",
                "testema@ukr.net", "1312345678", "12345678", new HashSet<>(), RegistrationStatus.NEW);

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userAccountUpdateRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenFirstNameAndLastNameIsLonger() throws ValidationException {
        String expectedMessage = "First name or last name is has more 16 symbols!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("JohnJohnJohnJohnJ", "DoeDOEDoeDOEDoeDOE", "testemail@ukr.net",
                "12345678", "12345678", RegistrationStatus.NEW, new HashSet<>());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenNotContainSpecialSymbol() throws ValidationException {
        String expectedMessage = "Email is not correct!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "testemailukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenDataContainSpecialCharacters() throws ValidationException {
        String expectedMessage = "Data cannot contain special characters!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("Joh@n", "!Doe", "testemail@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenPasswordDoesNotMatch() throws ValidationException {
        String expectedMessage = "Passwords does not match!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "testemail@ukr.net", "12345678",
                "123456", RegistrationStatus.NEW, new HashSet<>());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenLoginIsAlreadyExists() throws ValidationException {
        String expectedMessage = "This login already exists!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "dis@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>());
        UserAccount userAccount = new UserAccount("John", "Doe", "dis@ukr.net", "12345678", "12345678");

        when(userAccountRepository.getUserByEmail("dis@ukr.net")).thenReturn(Optional.of(userAccount));

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenUserIdIsNotExists() throws ValidationException {
        String expectedMessage = "This userId is not exists!";

        when(userAccountRepository.findById("wrong_id")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validateUserId("wrong_id"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoesNotThrowValidationExceptionWhenUserIdIsOkTest() throws ValidationException {
        UserAccount userAccount = new UserAccount("John", "Doe", "dis@ukr.net", "12345678", "12345678");

        when(userAccountRepository.findById("ok_id")).thenReturn(Optional.of(userAccount));

        assertDoesNotThrow(() -> userValidator.validateUserId("ok_id"));
    }

    @Test
    void checkMethodValidateRegRequestTest() throws ValidationException {
        String expectedMessage = "Passwords does not match!";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "testema@ukr.net", "1312345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>());

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userRegistrationRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void checkMethodValidateUpdateRequestTest() throws ValidationException {
        String expectedMessage = "Passwords does not match!";

        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe",
                "testema@ukr.net", "1312345678", "12345678", new HashSet<>(), RegistrationStatus.NEW);

        Exception exception = assertThrows(ValidationException.class, () -> userValidator.validate(userAccountUpdateRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void checkMethodValidatePasswordWhenIsOkTest() throws ValidationException {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe",
                "testema@ukr.net", "12345678", "12345678", new HashSet<>(), RegistrationStatus.NEW);

        assertDoesNotThrow(() -> userValidator.validate(userAccountUpdateRequest));
    }

    @Test
    void checkMethodValidatePasswordWhenUseRegRequestTest() throws ValidationException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "testema@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>());

        assertDoesNotThrow(() -> userValidator.validate(userRegistrationRequest));
    }

    @Test
    void checkMethodValidateDataRegRequestTest() throws ValidationException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "testema@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>());

        assertDoesNotThrow(() -> userValidator.validate(userRegistrationRequest));
    }

    @Test
    void checkMethodValidateDataUpdateRequestTest() throws ValidationException {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe",
                "testema@ukr.net", "12345678", "12345678", new HashSet<>(), RegistrationStatus.NEW);

        assertDoesNotThrow(() -> userValidator.validate(userAccountUpdateRequest));
    }
}
