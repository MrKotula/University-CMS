package ua.foxminded.university.validator;

import ua.foxminded.university.validator.exception.ValidationException;

public interface CourseValidator {
    void validateCourseName(String courseName) throws ValidationException;

    void validateCourseDescription(String courseDescription) throws ValidationException;
}
