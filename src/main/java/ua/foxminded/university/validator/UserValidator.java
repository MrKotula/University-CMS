package ua.foxminded.university.validator;

import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.validator.exception.ValidationException;

public interface UserValidator {
    
    void validateEmail(String email) throws ValidationException;

    void validateUserId(String userId) throws ValidationException;

    void validate(UserRegistrationRequest userRegistrationRequest) throws ValidationException;
}
