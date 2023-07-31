package ua.foxminded.university.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.UserValidator;

@ValidationService
@Log4j2
@AllArgsConstructor
public class UserValidatorImpl implements UserValidator {
    private static final int MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME = 16;
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

    private final UserAccountRepository userAccountRepository;

    @Override
    public void validateUserId(String userId) throws ValidationException {
        if (userAccountRepository.findById(userId).isEmpty()) {
            log.info("This userId is not exists!");
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
    public void validateEmail(String email) throws ValidationException {
        if (!email.contains("@")) {
            log.info("Email is not correct!");
            throw new ValidationException("Email is not correct!");
        }
    }

    private void validateData(String email, String firstName, String lastName) throws ValidationException {
        validateEmail(email);
        validationOnSpecialCharacters(firstName);
        validationOnSpecialCharacters(lastName);

        if (firstName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME || lastName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME) {
            log.info("First name or last name is has more 16 symbols!");
            throw new ValidationException("First name or last name is has more 16 symbols!");
        }
    }

    private void validationOnSpecialCharacters(String text) throws ValidationException {
        Matcher hasSpecial = SPECIAL.matcher(text);
        boolean matchFound = hasSpecial.find();

        if (matchFound) {
            log.info("Data cannot contain special characters!");
            throw new ValidationException("Data cannot contain special characters!");
        }
    }

    private void validatePassword(String password, String passwordCheck) throws ValidationException {
        if (!password.equals(passwordCheck)) {
            throw new ValidationException("Passwords does not match!");
        }
    }

    private void validateLogin(String email) throws ValidationException {
        if (userAccountRepository.getUserByEmail(email).isEmpty()) {

        } else if (email.equals(userAccountRepository.getUserByEmail(email).get().getEmail())) {
            throw new ValidationException("This login already exists!");
        }
    }
}
