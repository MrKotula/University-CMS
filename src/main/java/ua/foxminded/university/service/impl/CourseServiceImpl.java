package ua.foxminded.university.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.validator.CourseValidator;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseValidator courseValidator;
    private final CourseRepository courseRepository;

    @Override
    public void register(String courseName, String courseDescription) throws ValidationException {
        courseValidator.validateCourseName(courseName);
        courseValidator.validateCourseDescription(courseDescription);

        courseRepository.save(new Course(courseName, courseDescription));
    }

    @Override
    public void updateCourseName(Course course) throws ValidationException {
        courseValidator.validateCourseName(course.getCourseName());

        courseRepository.save(course);
    }

    @Override
    public void updateCourseDescription(Course course) throws ValidationException {
        courseValidator.validateCourseDescription(course.getCourseDescription());

        courseRepository.save(course);
    }

    @Override
    public List<Course> findByStudentId(String userId) {
        return courseRepository.findByStudentId(userId);
    }

    @Override
    public List<Course> getCoursesMissingByStudentId(String userId) {
        return courseRepository.getCoursesMissingByStudentId(userId);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public CourseResponse getCourseById(String courseId) {
        Course course = courseRepository.findById(courseId).get();

        return new CourseResponse(course.getCourseId(), course.getCourseName(), course.getCourseDescription(), course.getTeachers(),
                course.getNumberOfSeats(), countAvailableSeats(courseId));
    }

    private int countAvailableSeats(String courseId) {
        Course course = courseRepository.findById(courseId).get();

        int availableSeats = course.getNumberOfSeats() - courseRepository.getCountedCoursesSeats(courseId);

        course.setSeatsAvailable(availableSeats);
        courseRepository.save(course);

        return availableSeats;
    }
}
