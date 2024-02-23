package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.service.mapper.CourseMapper;
import ua.foxminded.university.service.mapper.TeacherAccountMapper;
import ua.foxminded.university.validator.CourseValidator;
import ua.foxminded.university.validator.exception.CourseException;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseValidator courseValidator;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private TeacherAccountMapper teacherAccountMapper;

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
        CourseRequest testCourseRequest = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        when(courseMapper.transformCourseFromDto(testCourseRequest)).thenReturn(testCourse);

        courseService.updateCourseName(testCourseRequest);

        verify(courseValidator).validateCourseName("testCourse");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void verifyUseMethodUpdateCourseDescription() throws ValidationException {
        CourseRequest testCourseRequest = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        when(courseMapper.transformCourseFromDto(testCourseRequest)).thenReturn(testCourse);

        courseService.updateCourseDescription(testCourseRequest);

        verify(courseValidator).validateCourseDescription("testDescription");
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void shouldReturnListOfCoursesWhenUseFindByStudentId() {
        CourseResponse testCourseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        List<CourseResponse> courseResponseList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();

        courseResponseList.add(testCourseResponse);
        courseList.add(testCourse);

        when(courseRepository.findByStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(courseList);
        when(courseMapper.transformListCourseToDto(courseList)).thenReturn(courseResponseList);

        assertEquals(courseResponseList, courseService.findByStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
        verify(courseRepository).findByStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
    }

    @Test
    void shouldReturnListOfCoursesWhenUseGetCoursesMissingByStudentId() {
        CourseResponse testCourseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        List<CourseResponse> courseResponseList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();

        courseResponseList.add(testCourseResponse);
        courseList.add(testCourse);

        when(courseRepository.getCoursesMissingByStudentId("1d95bc79-a549-4d2c-aeb5-3f929aee1234")).thenReturn(courseList);
        when(courseMapper.transformListCourseToDto(courseList)).thenReturn(courseResponseList);

        assertEquals(courseResponseList, courseService.getCoursesMissingByStudentId("1d95bc79-a549-4d2c-aeb5-3f929aee1234"));
        verify(courseRepository).getCoursesMissingByStudentId("1d95bc79-a549-4d2c-aeb5-3f929aee1234");
    }

    @Test
    void shouldReturnListOfCoursesWhenUseFindAllCoursesTest() {
        List<CourseResponse> listOfCourseResponses = new ArrayList<>();
        List<Course> listOfCourses = new ArrayList<>();

        CourseResponse courseResponseEnglish = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee7658", "English", "course of English", new ArrayList<>(), 30, 30);
        Course courseEnglish = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee7658", "English", "course of English", new ArrayList<>(), 30, 30, 1);

        listOfCourseResponses.add(courseResponseEnglish);
        listOfCourses.add(courseEnglish);

        when(courseRepository.findAll()).thenReturn(listOfCourses);
        when(courseMapper.transformListCourseToDto(listOfCourses)).thenReturn(listOfCourseResponses);

        assertEquals(listOfCourseResponses, courseService.getAllCourses());
        verify(courseRepository).findAll();
    }

    @Test
    void shouldReturnCourseResponseWhenUseGetCourseByIdTest() {
        Course course = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee7658", "English", "course of English", new ArrayList<>(), 30, 30, 1);

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee7658")).thenReturn(Optional.of(course));
        when(teacherAccountMapper.transformListTeachersToDto(course.getTeachers())).thenReturn(new ArrayList<>());

        CourseResponse courseResponse = courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee7658");

        assertEquals("English", courseResponse.getCourseName());
        assertEquals("course of English", courseResponse.getCourseDescription());
        assertEquals("1d95bc79-a549-4d2c-aeb5-3f929aee7658", courseResponse.getCourseId());
    }

    @Test
    void shouldRemoveCourseWhenUseRemoveCourseTest() {
        Course course = mock(Course.class);

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee7658")).thenReturn(Optional.of(course));
        when(course.getNumberOfSeats()).thenReturn(30);
        when(course.getSeatsAvailable()).thenReturn(30);

        courseService.removeCourse("1d95bc79-a549-4d2c-aeb5-3f929aee7658");

        verify(courseRepository).removeCourse("1d95bc79-a549-4d2c-aeb5-3f929aee7658");
    }

    @Test
    void shouldThrowExceptionWhenUseRemoveCourseTest() {
        String exceptedMessage = "Students are enrolled in this course";
        Course course = mock(Course.class);

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee7658")).thenReturn(Optional.of(course));
        when(course.getNumberOfSeats()).thenReturn(30);
        when(course.getSeatsAvailable()).thenReturn(29);

        Exception exception = assertThrows(CourseException.class, () -> courseService.removeCourse("1d95bc79-a549-4d2c-aeb5-3f929aee7658"));

        assertEquals(exceptedMessage, exception.getMessage());
    }
}
