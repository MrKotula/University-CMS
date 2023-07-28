package ua.foxminded.university.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.UserValidator;

@ValidationService
@Log4j2
public class UserValidatorImpl implements UserValidator {
    private static final int MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME = 16;
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

    UserAccountRepository userAccountRepository;

    @Autowired
    public UserValidatorImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public void validateData(String email, String firstName, String lastName) throws ValidationException {
        validateEmail(email);
        validationOnSpecialCharacters(firstName);
        validationOnSpecialCharacters(lastName);

        if (firstName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME || lastName.length() > MAX_LENGTH_OF_FIRSTNAME_OR_LASTNAME) {
            log.info("First name or last name is has more 16 symbols!");
            throw new ValidationException("First name or last name is has more 16 symbols!");
        }
    }

    @Override
    public void validateEmail(String email) throws ValidationException {
        if (!email.contains("@")) {
            log.info("Email is not correct!");
            throw new ValidationException("Email is not correct!");
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

    @Override
    public void validatePassword(String password, String passwordCheck) throws ValidationException {
        if (!password.equals(passwordCheck)) {
            throw new ValidationException("Passwords does not match!");
        }
    }

    @Override
    public void validateLogin(String email) throws ValidationException {
        if (userAccountRepository.getUserByEmail(email) == null) {

        } else if(email.equals(userAccountRepository.getUserByEmail(email).getEmail())) {
            throw new ValidationException("This login already exists!");
        }
    }
}
