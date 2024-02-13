package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.service.RoleService;
import ua.foxminded.university.service.dto.response.RoleResponse;
import ua.foxminded.university.service.mapper.RoleMapper;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponse> findAllRoles() {
        return roleMapper.transformListRoleResponseFromListRole(roleRepository.findAll());
    }
}
