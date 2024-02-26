package ua.foxminded.university.validator;

import ua.foxminded.university.entity.Group;
import ua.foxminded.university.validator.exception.ValidationException;

public interface GroupValidator {
    void validateGroupName(String groupName) throws ValidationException;

    void validateGroupId(String userId) throws ValidationException;

    void validateStudentsInGroupBeforeRemove(Group group) throws ValidationException;
}
