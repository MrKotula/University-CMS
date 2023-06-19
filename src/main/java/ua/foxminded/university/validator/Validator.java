package ua.foxminded.university.validator;

import ua.foxminded.university.exceptions.ValidationException;

public interface Validator {
    void validateData(String email, String firstName, String lastName) throws ValidationException;
    
    void validateEmail(String email) throws ValidationException;
}
