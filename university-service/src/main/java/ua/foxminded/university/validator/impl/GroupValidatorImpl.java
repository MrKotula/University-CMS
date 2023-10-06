package ua.foxminded.university.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.exception.ValidationException;

@ValidationService
@AllArgsConstructor
public class GroupValidatorImpl implements GroupValidator {
    private static final Pattern SPECIAL_GROUP_PATTERN = Pattern.compile("^[A-Z]{2}-\\d{2}$");

    private final GroupRepository groupRepository;

    @Override
    public void validateGroupName(String groupName) throws ValidationException {
        validationOnSpecialGroupPattern(groupName);
    }

    private void validationOnSpecialGroupPattern(String text) throws ValidationException {
        Matcher hasSpecial = SPECIAL_GROUP_PATTERN.matcher(text);
        boolean matchFound = hasSpecial.find();

        if (!matchFound) {
            throw new ValidationException("Group name cannot special format for group!");
        }
    }

    @Override
    public void validateGroupId(String userId) throws ValidationException {
        if (groupRepository.findById(userId).isEmpty()) {
            throw new ValidationException("This groupId is not exists!");
        }
    }
}
