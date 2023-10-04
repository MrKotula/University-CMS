package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.service.dto.updateData.UserAccountUpdateRequest;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.service.mapper.UserUpdateMapper;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.lang.String.valueOf;

@Service
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;

    private final UserUpdateMapper userUpdateMapper = Mappers.getMapper(UserUpdateMapper.class);

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

    @Override
    public UserAccountUpdateRequest getUserByEmail(String email) {
        UserAccount userAccount = userAccountRepository.getUserByEmail(email).get();

        return new UserAccountUpdateRequest(userAccount.getUserId(), userAccount.getFirstName(), userAccount.getLastName(), userAccount.getEmail(),
                userAccount.getPassword(), userAccount.getPasswordCheck(), userAccount.getRoles(), userAccount.getRegistrationStatus());
    }

    @Override
    public List<UserAccount> findAllUsers() {
        return userAccountRepository.findAll();
    }

    @Override
    public UserAccountUpdateRequest findUserById(String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).get();

        return new UserAccountUpdateRequest(userAccount.getUserId(), userAccount.getFirstName(), userAccount.getLastName(), userAccount.getEmail(),
                userAccount.getPassword(), userAccount.getPasswordCheck(), userAccount.getRoles(), userAccount.getRegistrationStatus());
    }

    @Override
    public void updateUserData(UserAccountUpdateRequest userAccountUpdateRequest) {
        userValidator.validateUserId(userAccountUpdateRequest.getUserId());
        UserAccount userAccount = userAccountRepository.findById(userAccountUpdateRequest.getUserId()).get();

        if(userAccountUpdateRequest.getFirstName().isEmpty()) {
            userAccount.setFirstName(userAccount.getFirstName());
        } else {
            userAccount.setFirstName(userAccountUpdateRequest.getFirstName());
        }
        if(userAccountUpdateRequest.getLastName().isEmpty()) {
            userAccount.setLastName(userAccount.getLastName());
        } else {
            userAccount.setLastName(userAccountUpdateRequest.getLastName());
        }
        if(userAccountUpdateRequest.getEmail().isEmpty()) {
            userAccount.setEmail(userAccount.getEmail());
        } else {
            userValidator.validateEmail(userAccountUpdateRequest.getEmail());
            userAccount.setEmail(userAccountUpdateRequest.getEmail());
        }
        if(userAccountUpdateRequest.getPassword().isEmpty()) {
            userAccount.setPassword(userAccount.getPassword());
        } else {
            userAccount.setPassword(passwordEncoder.encode(userAccountUpdateRequest.getPassword()));
        }
        if(userAccountUpdateRequest.getPasswordCheck().isEmpty()) {
            userAccount.setPasswordCheck(userAccount.getPasswordCheck());
        } else {
            userAccount.setPasswordCheck(passwordEncoder.encode(userAccountUpdateRequest.getPasswordCheck()));
        }
        userValidator.validate(userAccountUpdateRequest);

        userAccountRepository.save(userAccount);
    }

    @Override
    public void updateUserRoles(UserAccountUpdateRequest userAccountUpdateRequest, String roles) {
        UserAccount userAccount = userAccountRepository.findById(userAccountUpdateRequest.getUserId()).get();

        String[] separetedRoles = roles.trim().split(",");
        Set<Role> setRoles = userAccountUpdateRequest.getRoles();

        for (String separetedRole : separetedRoles) {
            if (separetedRole.equals(valueOf(RoleModel.USER))) {
                Role roleUser = roleRepository.findByRole(RoleModel.USER);
                setRoles.add(roleUser);
            } else if (separetedRole.equals(valueOf(RoleModel.TEACHER))) {
                Role roleTeacher = roleRepository.findByRole(RoleModel.TEACHER);
                setRoles.add(roleTeacher);
            } else if (separetedRole.equals(valueOf(RoleModel.STUDENT))) {
                Role roleStudent = roleRepository.findByRole(RoleModel.STUDENT);
                setRoles.add(roleStudent);
            } else if (separetedRole.equals(valueOf(RoleModel.MODERATOR))) {
                Role roleModerator = roleRepository.findByRole(RoleModel.MODERATOR);
                setRoles.add(roleModerator);
            } else if (separetedRole.equals(valueOf(RoleModel.ADMIN))) {
                Role roleAdmin = roleRepository.findByRole(RoleModel.ADMIN);
                setRoles.add(roleAdmin);
            }
        }

        userAccountUpdateRequest.setRoles(setRoles);
        userUpdateMapper.updateUserAccountFromDto(userAccountUpdateRequest, userAccount);

        userAccountRepository.save(userAccount);
    }
}
