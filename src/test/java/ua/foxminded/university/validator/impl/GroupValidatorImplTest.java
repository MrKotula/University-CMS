package ua.foxminded.university.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.GroupService;

@SpringBootTest
class GroupValidatorImplTest {

    @Autowired
    GroupService groupService;

    @Test
    void shouldReturnValidationExceptionWhenGroupNameCannotSpecialCharacter() throws ValidationException {
        String expectedMessage = "Group name cannot special format for group!";
        Exception exception = assertThrows(ValidationException.class, () -> groupService.register("test"));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
