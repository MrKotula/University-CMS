package ua.foxminded.university.validator;

import ua.foxminded.university.validator.exception.ValidationException;

public interface UserValidator {
    void validateData(String email, String firstName, String lastName) throws ValidationException;
    
    void validateEmail(String email) throws ValidationException;
    
    void validatePassword(String password, String passwordCheck) throws ValidationException;

    void validateLogin(String email) throws ValidationException;
}
