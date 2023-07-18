package ua.foxminded.university.validator.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.enums.RegistrationStatus;
import ua.foxminded.university.enums.Status;
import ua.foxminded.university.exceptions.ValidationException;
import ua.foxminded.university.service.StudentService;

@SpringBootTest
class UserValidatorImplTest {
    
    @Autowired
    StudentService studentService;
    
    @Test
    void shouldReturnValidationExceptionWhenFirstNameIsLonger() throws ValidationException {
	String expectedMessage = "First name or last name is has more 16 symbols!";
	Exception exception = assertThrows(ValidationException.class, () -> studentService.register(new UserDto("JohnJohnJohnJohnF", "Doe", "testemail@ukr.net",
		"12345678", "12345678", Status.NEW, RegistrationStatus.NEW)));
	
	assertEquals(expectedMessage, exception.getMessage());
    }
    
    @Test
    void shouldReturnValidationExceptionWhenLastNameIsLonger() throws ValidationException {
	String expectedMessage = "First name or last name is has more 16 symbols!";
	Exception exception = assertThrows(ValidationException.class, () -> studentService.register(new UserDto("John", "DoeDOEDoeDOEDoeDOE", "testemail@ukr.net",
		"12345678", "12345678", Status.NEW, RegistrationStatus.NEW)));
	
	assertEquals(expectedMessage, exception.getMessage());
    }
    
    @Test
    void shouldReturnValidationExceptionWhenNotContainSpecialSymbol() throws ValidationException {
	String expectedMessage = "Email is not correct!";
	Exception exception = assertThrows(ValidationException.class, () -> studentService.register(new UserDto("John", "Doe", "testemailukr.net", "12345678",
		"12345678", Status.NEW, RegistrationStatus.NEW)));
	
	assertEquals(expectedMessage, exception.getMessage());
    }
    
    @Test
    void shouldReturnValidationExceptionWhenDataContainSpecialCharacters() throws ValidationException {
	String expectedMessage = "Data cannot contain special characters!";
	Exception exception = assertThrows(ValidationException.class, () -> studentService.register(new UserDto("Joh@n", "!Doe", "testemail@ukr.net", "12345678",
		"12345678", Status.NEW, RegistrationStatus.NEW)));
	
	assertEquals(expectedMessage, exception.getMessage());
    }
    
    @Test
    void shouldReturnValidationExceptionWhenPasswordDoesNotMatch() throws ValidationException {
	String expectedMessage = "Passwords does not match!";
	Exception exception = assertThrows(ValidationException.class, () -> studentService.register(new UserDto("John", "Doe", "testemail@ukr.net", "12345678",
		"123456", Status.NEW, RegistrationStatus.NEW)));
	
	assertEquals(expectedMessage, exception.getMessage());
    }
}
