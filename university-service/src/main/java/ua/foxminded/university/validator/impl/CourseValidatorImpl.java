package ua.foxminded.university.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.validator.CourseValidator;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.exception.ValidationException;

@ValidationService
@AllArgsConstructor
public class CourseValidatorImpl implements CourseValidator {
    private static final int MAX_LENGTH_OF_COURSE_NAME = 24;
    private static final int MAX_LENGTH_OF_COURSE_DESCRIPTION = 36;
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

    private final CourseRepository courseRepository;

    @Override
    public void validateCourseName(String courseName) throws ValidationException {
        validationOnSpecialCharacters(courseName);

        if (courseName.length() > MAX_LENGTH_OF_COURSE_NAME) {
            throw new ValidationException("Course name is has more 24 symbols!");
        }
    }

    @Override
    public void validateCourseDescription(String courseDescription) throws ValidationException {
        validationOnSpecialCharacters(courseDescription);

        if (courseDescription.length() > MAX_LENGTH_OF_COURSE_DESCRIPTION) {
            throw new ValidationException("Course description is has more 36 symbols!");
        }
    }

    private void validationOnSpecialCharacters(String text) throws ValidationException {
        Matcher hasSpecial = SPECIAL.matcher(text);
        boolean matchFound = hasSpecial.find();

        if (matchFound) {
            throw new ValidationException("Data cannot contain special characters!");
        }
    }

    @Override
    public void validateCourseId(String courseId) throws ValidationException {
        if (courseRepository.findById(courseId).isEmpty()) {
            throw new ValidationException("This courseId is not exists!");
        }
    }

    @Override
    public void validateAvailableCourseSeatsBeforeRemove(Course course) throws ValidationException {
        if (course.getNumberOfSeats() != course.getSeatsAvailable()) {
            throw new ValidationException("Students are enrolled in this course");
        }
    }
}
