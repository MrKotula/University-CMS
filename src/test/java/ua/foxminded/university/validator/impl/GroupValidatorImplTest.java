package ua.foxminded.university.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.GroupService;

@SpringBootTest
class GroupValidatorImplTest {

    @Autowired
    GroupService groupService;

    @Autowired
    StudentAccountService studentAccountService;

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
