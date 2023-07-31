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
        userValidator.validate(userRegistrationRequest);

        Role role = roleRepository.findByRole(RoleModel.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        UserAccount newUserAccount = UserAccount.userAccountBuilder()
                .firstName(userRegistrationRequest.getFirstName().trim())
                .lastName(userRegistrationRequest.getLastName().trim())
                .email(userRegistrationRequest.getEmail().trim())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword().trim()))
                .passwordCheck(passwordEncoder.encode(userRegistrationRequest.getPasswordCheck().trim()))
                .registrationStatus(RegistrationStatus.NEW)
                .roles(roles)
                .build();

        userAccountRepository.save(newUserAccount);
    }
}
