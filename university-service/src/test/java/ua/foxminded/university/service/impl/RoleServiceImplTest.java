package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.service.dto.response.RoleResponse;
import ua.foxminded.university.service.mapper.RoleMapper;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role roleAdmin = new Role("54RG9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.ADMIN);
    private Role roleModerator = new Role("64TR9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.MODERATOR);
    private Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);
    private Role roleTeacher = new Role("PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.TEACHER);
    private Role roleUser = new Role("LDG69439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.USER);

    private RoleResponse roleResponseAdmin = new RoleResponse("54RG9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.ADMIN);
    private RoleResponse roleResponseModerator = new RoleResponse("64TR9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.MODERATOR);
    private RoleResponse roleResponseStudent = new RoleResponse("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);
    private RoleResponse roleResponseTeacher = new RoleResponse("PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.TEACHER);
    private RoleResponse roleResponseUser = new RoleResponse("LDG69439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.USER);

    @Test
    void shouldReturnListOfRolesWhenUseFindAllRoles() {
        List<Role> listAllRoles = Arrays.asList(roleAdmin, roleModerator, roleStudent, roleTeacher, roleUser);
        List<RoleResponse> listAllResponseRoles = Arrays.asList(roleResponseAdmin, roleResponseModerator, roleResponseStudent, roleResponseTeacher, roleResponseUser);

        when(roleRepository.findAll()).thenReturn(listAllRoles);
        when(roleMapper.transformListRoleResponseFromListRole(listAllRoles)).thenReturn(listAllResponseRoles);

        assertEquals(listAllResponseRoles, roleService.findAllRoles());
        verify(roleRepository).findAll();
    }
}
