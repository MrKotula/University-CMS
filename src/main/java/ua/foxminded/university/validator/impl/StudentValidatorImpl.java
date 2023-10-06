package ua.foxminded.university.validator.impl;

import lombok.AllArgsConstructor;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.validator.CourseValidator;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.StudentValidator;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.Set;

@ValidationService
@AllArgsConstructor
public class StudentValidatorImpl implements StudentValidator {

    private static final int MAX_AMOUNT_COURSES = 7;

    private final UserValidator userValidator;
    private final CourseValidator courseValidator;
    private final GroupValidator groupValidator;
    private final CourseRepository courseRepository;

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

    @Override
    public boolean validateAvailableCourses(StudentAccountResponse studentAccountResponse, String courseId) throws ValidationException {
        Set<Course> listOfCouses = studentAccountResponse.getCourses();
        Course newCourse = courseRepository.findById(courseId).get();

        boolean isTrue = false;

        if(!listOfCouses.contains(newCourse)) {
            isTrue = true;
        }

        return isTrue;
    }

    @Override
    public void validateMaxAvailableCourses(StudentAccountResponse studentAccountResponse) throws ValidationException {
        Set<Course> listOfCouses = studentAccountResponse.getCourses();

        if(listOfCouses.size() >= MAX_AMOUNT_COURSES) {
            throw new ValidationException("You have a max available courses!");
        }
    }
}
