package ua.foxminded.university.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.mapper.CourseMapper;
import ua.foxminded.university.service.mapper.TeacherAccountMapper;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.validator.CourseValidator;

@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseValidator courseValidator;
    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;
    private final TeacherAccountMapper teacherAccountMapper;

    @Override
    public void register(String courseName, String courseDescription) throws ValidationException {
        courseValidator.validateCourseName(courseName);
        courseValidator.validateCourseDescription(courseDescription);

        courseRepository.save(new Course(courseName, courseDescription));
    }

    @Override
    public void updateCourseName(CourseRequest courseRequest) throws ValidationException {
        courseValidator.validateCourseName(courseRequest.getCourseName());

        courseRepository.save(courseMapper.transformCourseFromDto(courseRequest));
    }

    @Override
    public void updateCourseDescription(CourseRequest courseRequest) throws ValidationException {
        courseValidator.validateCourseDescription(courseRequest.getCourseDescription());

        courseRepository.save(courseMapper.transformCourseFromDto(courseRequest));
    }

    @Override
    public List<CourseResponse> findByStudentId(String userId) {
        return courseMapper.transformListCourseToDto(courseRepository.findByStudentId(userId));
    }

    @Override
    public List<CourseResponse> getCoursesMissingByStudentId(String userId) {
        return courseMapper.transformListCourseToDto(courseRepository.getCoursesMissingByStudentId(userId));
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseMapper.transformListCourseToDto(courseRepository.findAll());
    }

    @Override
    public CourseResponse getCourseById(String courseId) {
        Course course = courseRepository.findById(courseId).get();

        return new CourseResponse(course.getCourseId(), course.getCourseName(), course.getCourseDescription(),
                teacherAccountMapper.transformListTeachersToDto(course.getTeachers()), course.getNumberOfSeats(), countAvailableSeats(courseId), course.getVersion());
    }

    private int countAvailableSeats(String courseId) {
        Course course = courseRepository.findById(courseId).get();

        int availableSeats = course.getNumberOfSeats() - courseRepository.getCountedCoursesSeats(courseId);

        course.setSeatsAvailable(availableSeats);
        courseRepository.save(course);

        return availableSeats;
    }

    @Override
    public void removeCourse(String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found!"));

        courseValidator.validateAvailableCourseSeatsBeforeRemove(course);

        courseRepository.removeCourse(courseId);
    }
}
