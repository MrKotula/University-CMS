package ua.foxminded.university.validator.impl;

import lombok.AllArgsConstructor;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ValidationService
@AllArgsConstructor
public class UserValidatorImpl implements UserValidator {
    private static final int MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME = 16;
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

    private final UserAccountRepository userAccountRepository;

    @Override
    public void validateUserId(String userId) throws ValidationException {
        if (userAccountRepository.findById(userId).isEmpty()) {
            throw new ValidationException("This userId is not exists!");
        }
    }

    @Override
    public void validate(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
        validateLogin(userRegistrationRequest.getEmail().trim());
        validateData(userRegistrationRequest.getEmail().trim(), userRegistrationRequest.getFirstName().trim(), userRegistrationRequest.getLastName().trim());
        validatePassword(userRegistrationRequest.getPassword().trim(), userRegistrationRequest.getPasswordCheck().trim());
    }

    @Override
    public void validate(UserAccountUpdateRequest userAccountUpdateRequest) throws ValidationException {
        validateLogin(userAccountUpdateRequest.getEmail().trim());
        validateData(userAccountUpdateRequest.getFirstName().trim(), userAccountUpdateRequest.getLastName().trim());
        validatePassword(userAccountUpdateRequest.getPassword().trim(), userAccountUpdateRequest.getPasswordCheck().trim());
    }

    @Override
    public void validateEmail(String email) throws ValidationException {
        if (!email.contains("@")) {
            throw new ValidationException("Email is not correct!");
        }
    }

    private void validateData(String email, String firstName, String lastName) throws ValidationException {
        validateEmail(email);
        validationOnSpecialCharacters(firstName);
        validationOnSpecialCharacters(lastName);

        if (firstName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME || lastName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME) {
            throw new ValidationException("First name or last name is has more 16 symbols!");
        }
    }

    private void validateData(String firstName, String lastName) throws ValidationException {
        validationOnSpecialCharacters(firstName);
        validationOnSpecialCharacters(lastName);

        if (firstName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME || lastName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME) {
            throw new ValidationException("First name or last name is has more 16 symbols!");
        }
    }

    private void validationOnSpecialCharacters(String text) throws ValidationException {
        Matcher hasSpecial = SPECIAL.matcher(text);
        boolean matchFound = hasSpecial.find();

        if (matchFound) {
            throw new ValidationException("Data cannot contain special characters!");
        }
    }

    private void validatePassword(String password, String passwordCheck) throws ValidationException {
        if (!password.equals(passwordCheck)) {
            throw new ValidationException("Passwords does not match!");
        }
    }

    private void validateLogin(String email) throws ValidationException {
        if (!userAccountRepository.getUserByEmail(email).isEmpty()) {
            throw new ValidationException("This login already exists!");
        }
    }
}
