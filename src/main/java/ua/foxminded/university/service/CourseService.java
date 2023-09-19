package ua.foxminded.university.service;

import java.util.List;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.validator.exception.ValidationException;

public interface CourseService {
    void register(String courseName, String courseDescription) throws ValidationException;

    void updateCourseName(Course course) throws ValidationException;

    void updateCourseDescription(Course course) throws ValidationException;

    List<Course> findByStudentId(String userId);

    List<Course> getCoursesMissingByStudentId(String userId);

    List<Course> findAllCourses();

    CourseResponse getCourseById(String email);
}
