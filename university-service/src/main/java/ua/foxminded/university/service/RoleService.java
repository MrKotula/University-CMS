package ua.foxminded.university.service;

import ua.foxminded.university.service.dto.response.RoleResponse;
import java.util.List;

public interface RoleService {
    List<RoleResponse> findAllRoles();
}
