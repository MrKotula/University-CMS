package ua.foxminded.university.validator;

import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.validator.exception.ValidationException;

public interface StudentValidator {
    void validateStudent(UserRegistrationRequest userRegistrationRequest);

    void validateStudentId(String studentId) throws ValidationException;

    void validateGroupId(String groupId) throws ValidationException;

    void validateEmail(String email) throws ValidationException;

    void validateCourseId(String courseId) throws ValidationException;
}
