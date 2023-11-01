package ua.foxminded.university.validator;

import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.validator.exception.ValidationException;

public interface StudentValidator {
    void validateStudent(UserRegistrationRequest userRegistrationRequest);

    void validateStudentId(String studentId) throws ValidationException;

    void validateGroupId(String groupId) throws ValidationException;

    void validateEmail(String email) throws ValidationException;

    void validateCourseId(String courseId) throws ValidationException;

    boolean validateAvailableCourses(StudentAccountResponse studentAccountResponse, String courseId) throws ValidationException;

    void validateMaxAvailableCourses(StudentAccountResponse studentAccountResponse) throws ValidationException;
}
