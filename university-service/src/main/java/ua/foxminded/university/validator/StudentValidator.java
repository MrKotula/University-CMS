package ua.foxminded.university.validator;

import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.request.StudentAccountRequest;
import ua.foxminded.university.validator.exception.ValidationException;

public interface StudentValidator {
    void validateStudent(UserRegistrationRequest userRegistrationRequest);

    void validateStudentId(String studentId) throws ValidationException;

    void validateGroupId(String groupId) throws ValidationException;

    void validateEmail(String email) throws ValidationException;

    void validateCourseId(String courseId) throws ValidationException;

    boolean validateAvailableCourses(StudentAccountRequest studentAccountRequest, String courseId) throws ValidationException;

    void validateMaxAvailableCourses(StudentAccountRequest studentAccountRequest) throws ValidationException;

    void validateStudent(StudentAccount studentAccount) throws ValidationException;

    void validateForSpecialStudentCardPattern(String text);
}
