package ua.foxminded.university.service;

import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.service.dto.updateData.UserAccountUpdateRequest;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.List;

public interface UserAccountService {
    void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException;

    UserAccountUpdateRequest getUserByEmail(String email);

    void updateUserData(UserAccountUpdateRequest userAccountUpdateRequest);

    void updateUserRoles(UserAccountUpdateRequest userAccountUpdateRequest, String roles);

    List<UserAccount> findAllUsers();

    UserAccountUpdateRequest findUserById(String userId);
}
