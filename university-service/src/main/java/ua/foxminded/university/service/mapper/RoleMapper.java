package ua.foxminded.university.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.service.dto.request.RoleRequest;
import ua.foxminded.university.service.dto.response.RoleResponse;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "roleId", source = "roleId")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "version", source = "version")
    @Named("toEntity")
    Role transformRoleFromDto(RoleResponse roleResponse);

    Role transformRoleFromDto(RoleRequest roleRequest);

    @Mapping(target = "roleId", source = "roleId")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "version", source = "version")
    @Named("toRoleRequest")
    RoleRequest transformRoleRequestFromRole(Role role);

    @Mapping(target = "roleId", source = "roleId")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "version", source = "version")
    @Named("toRoleResponse")
    RoleResponse transformRoleResponseFromRole(Role role);

    Set<RoleResponse> transformSetRoleResponseFromSetRole(Set<Role> role);

    Set<RoleRequest> transformSetRoleRequestFromSetRole(Set<Role> role);

    List<RoleResponse> transformListRoleResponseFromListRole(List<Role> role);

    List<RoleRequest> transformListRoleRequestFromListRole(List<Role> role);
}
