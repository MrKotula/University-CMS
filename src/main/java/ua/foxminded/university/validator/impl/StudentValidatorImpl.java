package ua.foxminded.university.validator.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.validator.CourseValidator;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.StudentValidator;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.exception.ValidationException;

@ValidationService
@Log4j2
@AllArgsConstructor
public class StudentValidatorImpl implements StudentValidator {
    private final UserValidator userValidator;
    private final CourseValidator courseValidator;
    private final GroupValidator groupValidator;

    @Override
    public void validateStudent(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
       userValidator.validate(userRegistrationRequest);
    }

    @Override
    public void validateEmail(String email) throws ValidationException {
        userValidator.validateEmail(email);
    }

    @Override
    public void validateStudentId(String studentId) throws ValidationException {
        userValidator.validateUserId(studentId);
    }

    @Override
    public void validateGroupId(String groupId) throws ValidationException {
        groupValidator.validateGroupId(groupId);
    }

    @Override
    public void validateCourseId(String courseId) throws ValidationException {
        courseValidator.validateCourseId(courseId);
    }
}
