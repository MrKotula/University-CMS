package ua.foxminded.university.validator;

import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.validator.exception.ValidationException;

public interface UserValidator {
    
    void validateEmail(String email) throws ValidationException;

    void validateUserId(String userId) throws ValidationException;

    void validate(UserRegistrationRequest userRegistrationRequest) throws ValidationException;

    void validate(UserAccountUpdateRequest userAccountUpdateRequest) throws ValidationException;
}
