package ua.foxminded.university.validator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.validator.CourseValidator;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentValidatorImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserValidator userValidator;

    @Mock
    private CourseValidator courseValidator;

    @Mock
    private GroupValidator groupValidator;

    @InjectMocks
    private StudentValidatorImpl studentValidator;

    @Test
    void shouldReturnValidationExceptionWhenMoreMaxAvailableCoursesTest() throws ValidationException {
        String expectedMessage = "You have a max available courses!";

        Course testCourseChemistry = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5324", "Chemistry", "course of Chemistry", 15);
        Course testCoursePhysics = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee6589", "Physics", "course of Physics", 15);
        Course testCoursePhilosophy = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee8999", "Philosophy", "course of Philosophy", 30);
        Course testCourseDrawing = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0096", "Drawing", "course of Drawing", 10);
        Course testCourseLiterature = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1222", "Literature", "course of Literature", 10);
        Course testCourseEnglish = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee7658", "English", "course of English", 30);
        Course testCourseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course testCourseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(testCourseChemistry, testCoursePhysics, testCoursePhilosophy,
                testCourseDrawing, testCourseLiterature, testCourseEnglish, testCourseBiology, testCourseMath));

        StudentAccountResponse studentAccountResponseMock = mock(StudentAccountResponse.class);

        when(studentAccountResponseMock.getCourses()).thenReturn(courses);

        Exception exception = assertThrows(ValidationException.class, () -> studentValidator.validateMaxAvailableCourses(studentAccountResponseMock));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoesNotThrowValidationExceptionWhenLessMaxAvailableCoursesTest() throws ValidationException {
        Course testCourseChemistry = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5324", "Chemistry", "course of Chemistry", 15);
        Course testCoursePhysics = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee6589", "Physics", "course of Physics", 15);
        Course testCoursePhilosophy = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee8999", "Philosophy", "course of Philosophy", 30);
        Course testCourseDrawing = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0096", "Drawing", "course of Drawing", 10);
        Course testCourseLiterature = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1222", "Literature", "course of Literature", 10);
        Course testCourseEnglish = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee7658", "English", "course of English", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(testCourseChemistry, testCoursePhysics, testCoursePhilosophy,
                testCourseDrawing, testCourseLiterature, testCourseEnglish));

        StudentAccountResponse studentAccountResponseMock = mock(StudentAccountResponse.class);

        when(studentAccountResponseMock.getCourses()).thenReturn(courses);

        assertDoesNotThrow(() -> studentValidator.validateMaxAvailableCourses(studentAccountResponseMock));
    }

    @Test
    void checkMethodValidateStudentTest() throws ValidationException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "dis@ukr.net", "1234", "1234");

        studentValidator.validateStudent(userRegistrationRequest);

        verify(userValidator).validate(userRegistrationRequest);
    }

    @Test
    void checkMethodValidateEmailTest() throws ValidationException {
        studentValidator.validateEmail("dis@ukr.net");

        verify(userValidator).validateEmail("dis@ukr.net");
    }

    @Test
    void checkMethodValidateGroupIdTest() throws ValidationException {
        studentValidator.validateGroupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432");

        verify(groupValidator).validateGroupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432");
    }

    @Test
    void checkMethodValidateAvailableCoursesTest() throws ValidationException {
        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);
        Course testCourseDrawing = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0096", "Drawing", "course of Drawing", 10);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee0096")).thenReturn(Optional.of(testCourseDrawing));

        studentValidator.validateAvailableCourses(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        verify(courseRepository).findById("1d95bc79-a549-4d2c-aeb5-3f929aee0096");
    }

    @Test
    void checkMethodValidateAvailableCoursesWhenCourseAlreadyUseTest() throws ValidationException {
        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee1234")).thenReturn(Optional.of(courseBiology));

        studentValidator.validateAvailableCourses(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee1234");

        verify(courseRepository).findById("1d95bc79-a549-4d2c-aeb5-3f929aee1234");
    }

    @Test
    void checkMethodValidateStudentIdTest() throws ValidationException {
        studentValidator.validateStudentId("student_id");

        verify(userValidator).validateUserId("student_id");
    }

    @Test
    void checkMethodCourseIdTest() throws ValidationException {
        studentValidator.validateCourseId("course_id");

        verify(courseValidator).validateCourseId("course_id");
    }
}
