package ua.foxminded.university.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.service.dto.response.UserAccountResponse;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "version", source = "version")
    UserAccount transformUserAccountFromDtoResponse(UserAccountResponse userAccountResponse);

    UserAccount transformUserAccountFromDtoRequest(UserAccountUpdateRequest userAccountUpdateRequest);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "version", source = "version")
    UserAccountResponse transformUserAccountToDtoResponse(UserAccount userAccount);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "passwordCheck", source = "passwordCheck")
    @Mapping(target = "registrationStatus", source = "registrationStatus")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "version", source = "version")
    UserAccountUpdateRequest transformUserAccountToDtoRequest(UserAccount userAccount);

    List<UserAccountUpdateRequest> transformListUserAccountToDtoRequest(List<UserAccount> userAccount);

    List<UserAccountResponse> transformListUserAccountToDtoResponse(List<UserAccount> userAccount);
}
