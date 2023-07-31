package ua.foxminded.university.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.GroupValidator;

@ValidationService
@AllArgsConstructor
@Log4j2
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
            log.info("Group name cannot special format for group!");
            throw new ValidationException("Group name cannot special format for group!");
        }
    }

    @Override
    public void validateGroupId(String userId) throws ValidationException {
        if (groupRepository.findById(userId).isEmpty()) {
            log.info("This groupId is not exists!");
            throw new ValidationException("This groupId is not exists!");
        }
    }
}
