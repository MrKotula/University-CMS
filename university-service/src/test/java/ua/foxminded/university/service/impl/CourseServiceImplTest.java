package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.validator.CourseValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseValidator courseValidator;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course testCourse = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

    @Test
    void verifyUseMethodRegister() throws ValidationException {
        courseService.register("testCourse", "testDescription");

        verify(courseValidator).validateCourseName("testCourse");
        verify(courseValidator).validateCourseDescription("testDescription");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void verifyUseMethodUpdateCourseName() throws ValidationException {
        courseService.updateCourseName(testCourse);

        verify(courseValidator).validateCourseName("testCourse");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void verifyUseMethodUpdateCourseDescription() throws ValidationException {
        courseService.updateCourseDescription(testCourse);

        verify(courseValidator).validateCourseDescription("testDescription");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void shouldReturnListOfCoursesWhenUseFindByStudentId() {
        courseService.findByStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

        verify(courseRepository).findByStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
    }

    @Test
    void shouldReturnListOfCoursesWhenUseGetCoursesMissingByStudentId() {
        courseService.getCoursesMissingByStudentId("1d95bc79-a549-4d2c-aeb5-3f929aee1234");

        verify(courseRepository).getCoursesMissingByStudentId("1d95bc79-a549-4d2c-aeb5-3f929aee1234");
    }

    @Test
    void shouldReturnListOfCoursesWhenUseFindAllCoursesTest() {
        courseService.findAllCourses();

        verify(courseRepository).findAll();
    }

    @Test
    void shouldReturnCourseResponseWhenUseGetCourseByIdTest() {
        Course course = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee7658", "English", "course of English", new ArrayList<>(), 30, 30);

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee7658")).thenReturn(Optional.of(course));

        CourseResponse courseResponse = courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee7658");

        assertEquals("English", courseResponse.getCourseName());
        assertEquals("course of English", courseResponse.getCourseDescription());
        assertEquals("1d95bc79-a549-4d2c-aeb5-3f929aee7658", courseResponse.getCourseId());
    }
}
