package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role roleAdmin = new Role("54RG9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.ADMIN);
    private Role roleModerator = new Role("64TR9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.MODERATOR);
    private Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);
    private Role roleTeacher = new Role("PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.TEACHER);
    private Role roleUser = new Role("LDG69439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.USER);

    List<Role> listAllRoles = Arrays.asList(roleAdmin, roleModerator, roleStudent, roleTeacher, roleUser);

    @Test
    void shouldReturnListOfRolesWhenUseFindAllRoles() {
        when(roleRepository.findAll()).thenReturn(listAllRoles);

        assertEquals(listAllRoles, roleService.findAllRoles());
        verify(roleRepository).findAll();
    }
}
