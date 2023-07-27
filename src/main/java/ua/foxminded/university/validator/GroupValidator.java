package ua.foxminded.university.validator;

import ua.foxminded.university.validator.exception.ValidationException;

public interface GroupValidator {
    void validateGroupName(String groupName) throws ValidationException;
}
