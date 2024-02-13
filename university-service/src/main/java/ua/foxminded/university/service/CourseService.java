package ua.foxminded.university.service;

import java.util.List;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.validator.exception.ValidationException;

public interface CourseService {
    void register(String courseName, String courseDescription) throws ValidationException;

    void updateCourseName(CourseRequest courseRequest) throws ValidationException;

    void updateCourseDescription(CourseRequest courseRequest) throws ValidationException;

    List<CourseResponse> findByStudentId(String userId);

    List<CourseResponse> getCoursesMissingByStudentId(String userId);

    List<CourseResponse> getAllCourses();

    CourseResponse getCourseById(String userId);
}
