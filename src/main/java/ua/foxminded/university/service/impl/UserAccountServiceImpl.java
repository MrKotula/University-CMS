package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    private final RoleRepository roleRepository;

    @Override
    public void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
        userValidator.validateLogin(userRegistrationRequest.getEmail().trim());
        userValidator.validateData(userRegistrationRequest.getEmail().trim(), userRegistrationRequest.getFirstName().trim(), userRegistrationRequest.getLastName().trim());
        userValidator.validatePassword(userRegistrationRequest.getPassword().trim(), userRegistrationRequest.getPasswordCheck().trim());

        Role role = roleRepository.findByRole(RoleModel.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        UserAccount newUserAccount = new UserAccount(userRegistrationRequest.getFirstName().trim(), userRegistrationRequest.getLastName().trim(),
                userRegistrationRequest.getEmail().trim(), passwordEncoder.encode(userRegistrationRequest.getPassword().trim()),
                passwordEncoder.encode(userRegistrationRequest.getPasswordCheck().trim()), RegistrationStatus.NEW, roles);
        userAccountRepository.save(newUserAccount);
    }
}
