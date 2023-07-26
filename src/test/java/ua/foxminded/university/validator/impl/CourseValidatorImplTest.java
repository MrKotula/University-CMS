package ua.foxminded.university.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.CourseService;

@SpringBootTest
class CourseValidatorImplTest {

    @Autowired
    CourseService courseService;

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
}
