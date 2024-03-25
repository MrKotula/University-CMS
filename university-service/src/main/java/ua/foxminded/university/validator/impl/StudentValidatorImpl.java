package ua.foxminded.university.validator.impl;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.request.StudentAccountRequest;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.service.mapper.CourseMapper;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.validator.CourseValidator;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.StudentValidator;
import ua.foxminded.university.validator.ValidationService;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ValidationService
@AllArgsConstructor
public class StudentValidatorImpl implements StudentValidator {

    private static final int MAX_AMOUNT_COURSES = 7;
    private static final Pattern SPECIAL_STUDENT_CARD_PATTERN = Pattern.compile("^[A-Z]{2}\\d{8}$");

    private final UserValidator userValidator;
    private final CourseValidator courseValidator;
    private final GroupValidator groupValidator;
    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @Override
    public void validateStudent(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
       userValidator.validate(userRegistrationRequest);
    }

    @Override
    public void validateStudent(StudentAccount studentAccount) throws ValidationException {
        userValidator.validate(studentAccount);
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
    public boolean validateAvailableCourses(StudentAccountRequest studentAccountRequest, String courseId) throws ValidationException {
        Set<CourseRequest> listOfCourses = studentAccountRequest.getCourses();
        Course newCourse = courseRepository.findById(courseId).get();

        CourseRequest courseRequest = courseMapper.transformCourseToDtoRequest(newCourse);

        boolean isTrue = false;

        if(!listOfCourses.contains(courseRequest)) {
            isTrue = true;
        }

        return isTrue;
    }

    @Override
    public void validateMaxAvailableCourses(StudentAccountRequest studentAccountRequest) throws ValidationException {
        Set<CourseRequest> listOfCourses = studentAccountRequest.getCourses();

        if(listOfCourses.size() >= MAX_AMOUNT_COURSES) {
            throw new ValidationException("You have a max available courses!");
        }
    }

    @Override
    public void validateForSpecialStudentCardPattern(String text) throws ValidationException {
        Matcher hasSpecial = SPECIAL_STUDENT_CARD_PATTERN.matcher(text);
        boolean matchFound = hasSpecial.find();

        if (!matchFound) {
            throw new ValidationException("Student card has special format for field! Use like this format (GR83281023)");
        }
    }
}
