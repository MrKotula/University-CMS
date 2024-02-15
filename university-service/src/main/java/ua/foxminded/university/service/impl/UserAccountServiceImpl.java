package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.service.dto.response.UserAccountResponse;
import ua.foxminded.university.service.mapper.UserUpdateMapper;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;

    private final UserUpdateMapper userUpdateMapper;

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
    public UserAccountResponse getUserByEmail(String email) {
        UserAccount userAccount = userAccountRepository.getUserByEmail(email).get();

        return userUpdateMapper.transformUserAccountToDtoResponse(userAccount);
    }

    @Override
    public List<UserAccountResponse> findAllUsers() {
        List<UserAccount> userAccountList = userAccountRepository.findAll();

        return userUpdateMapper.transformListUserAccountToDtoResponse(userAccountList);
    }

    @Override
    public UserAccountResponse findUserById(String userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).get();

        return userUpdateMapper.transformUserAccountToDtoResponse(userAccount);
    }

    @Override
    public void updateUserData(UserAccountUpdateRequest userAccountUpdateRequest) {
        userValidator.validateUserId(userAccountUpdateRequest.getUserId());
        UserAccount userAccount = userAccountRepository.findById(userAccountUpdateRequest.getUserId()).get();

        if (userAccountUpdateRequest.getFirstName().isEmpty()) {
            userAccount.setFirstName(userAccount.getFirstName());
        } else {
            userAccount.setFirstName(userAccountUpdateRequest.getFirstName());
        }
        if (userAccountUpdateRequest.getLastName().isEmpty()) {
            userAccount.setLastName(userAccount.getLastName());
        } else {
            userAccount.setLastName(userAccountUpdateRequest.getLastName());
        }
        if (userAccountUpdateRequest.getEmail().isEmpty()) {
            userAccount.setEmail(userAccount.getEmail());
        } else {
            userValidator.validateEmail(userAccountUpdateRequest.getEmail());
            userAccount.setEmail(userAccountUpdateRequest.getEmail());
        }
        if (userAccountUpdateRequest.getPassword().isEmpty()) {
            userAccount.setPassword(userAccount.getPassword());
        } else {
            userAccount.setPassword(passwordEncoder.encode(userAccountUpdateRequest.getPassword()));
        }
        if (userAccountUpdateRequest.getPasswordCheck().isEmpty()) {
            userAccount.setPasswordCheck(userAccount.getPasswordCheck());
        } else {
            userAccount.setPasswordCheck(passwordEncoder.encode(userAccountUpdateRequest.getPasswordCheck()));
        }
        userValidator.validate(userAccountUpdateRequest);

        userAccountRepository.save(userAccount);
    }

    @Override
    public void updateUserRoles(UserAccountUpdateRequest userAccountUpdateRequest, String roles) {
        UserAccount userAccount = userAccountRepository.findById(userAccountUpdateRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        String[] separatedRoles = roles.trim().split(",");
        Set<Role> setRoles = userAccountUpdateRequest.getRoles();
        List<String> roleIds = new ArrayList<>();

        for (String separatedRole : separatedRoles) {
            RoleModel roleModel = RoleModel.valueOf(separatedRole);
            Role role = roleRepository.findByRole(roleModel);
            if (role != null) {
                setRoles.add(role);
                roleIds.add(role.getRoleId());
            }
        }

        List<String> userRoles = roleRepository.getUserRoles(userAccountUpdateRequest.getUserId());
        boolean roleExists = roleIds.stream().anyMatch(userRoles::contains);

        if (!roleExists) {
            if (!setRoles.isEmpty()) {
                userAccountUpdateRequest.setRoles(setRoles);
            }
            for (String roleId : roleIds) {
                roleRepository.insertNewRoles(userAccountUpdateRequest.getUserId(), roleId);
            }
        }

        userUpdateMapper.transformUserAccountFromDtoRequest(userAccountUpdateRequest);

        userAccountRepository.save(userAccount);
    }
}
