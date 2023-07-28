package ua.foxminded.university.service;

import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.validator.exception.ValidationException;

public interface UserAccountService {
    void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException;
}
