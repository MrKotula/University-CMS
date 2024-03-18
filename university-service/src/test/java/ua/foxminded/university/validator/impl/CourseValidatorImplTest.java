package ua.foxminded.university.validator.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CourseValidatorImplTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseValidatorImpl courseValidator;

    @Test
    void shouldReturnValidationExceptionWhenCourseNameIsLonger() throws ValidationException {
        String expectedMessage = "Course name is has more 24 symbols!";

        Exception exception = assertThrows(ValidationException.class, () -> courseValidator.validateCourseName("TestTestTestTestTestTestT"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenCourseDescriptionIsLonger() throws ValidationException {
        String expectedMessage = "Course description is has more 36 symbols!";

        Exception exception = assertThrows(ValidationException.class, () -> courseValidator.validateCourseDescription("TestTestTestTestTestTestTestTestTestT"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenCourseIncludeSpecialCharacters() throws ValidationException {
        String expectedMessage = "Data cannot contain special characters!";

        Exception exception = assertThrows(ValidationException.class, () -> courseValidator.validateCourseName("Test@"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldReturnValidationExceptionWhenCourseIdIsNotExists() throws ValidationException {
        String expectedMessage = "This courseId is not exists!";

        when(courseRepository.findById("wrong_id")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> courseValidator.validateCourseId("wrong_id"));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoesNotThrowValidationExceptionWhenCourseNameIsOkTest() throws ValidationException {
        assertDoesNotThrow(() -> courseValidator.validateCourseName("TestTestTestTestTestTes"));
    }

    @Test
    void shouldDoesNotThrowValidationExceptionWhenCourseDescriptionIsOkTest() throws ValidationException {
        assertDoesNotThrow(() -> courseValidator.validateCourseDescription("TestTestTestTestTestTestTestTestTes"));
    }

    @Test
    void shouldDoesNotThrowValidationExceptionWhenCourseIdIsOkTest() throws ValidationException {
        Course testCourse = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(testCourse));

        assertDoesNotThrow(() -> courseValidator.validateCourseId("1d95bc79-a549-4d2c-aeb5-3f929aee5432"));
    }

    @Test
    void shouldThrowExceptionWhenUseValidateStudentsInGroupBeforeRemoveTest() {
        String exceptedMessage = "Students are enrolled in this course";

        Course course = mock(Course.class);

        when(course.getNumberOfSeats()).thenReturn(30);
        when(course.getSeatsAvailable()).thenReturn(29);

        Exception exception = assertThrows(ValidationException.class, () -> courseValidator.validateAvailableCourseSeatsBeforeRemove(course));

        assertEquals(exceptedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNothingWhenUseValidateStudentsInGroupBeforeRemoveTest() {
        Course course = mock(Course.class);

        when(course.getNumberOfSeats()).thenReturn(30);
        when(course.getSeatsAvailable()).thenReturn(30);

        courseValidator.validateAvailableCourseSeatsBeforeRemove(course);
    }
}
