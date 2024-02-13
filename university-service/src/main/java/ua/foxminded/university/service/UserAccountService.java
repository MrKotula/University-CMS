package ua.foxminded.university.service;

import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.service.dto.response.UserAccountResponse;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.List;

public interface UserAccountService {
    void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException;

    UserAccountResponse getUserByEmail(String email);

    void updateUserData(UserAccountUpdateRequest userAccountUpdateRequest);

    void updateUserRoles(UserAccountUpdateRequest userAccountUpdateRequest, String roles);

    List<UserAccountResponse> findAllUsers();

    UserAccountResponse findUserById(String userId);
}
