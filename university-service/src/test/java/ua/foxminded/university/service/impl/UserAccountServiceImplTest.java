package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.request.StudentAccountRequest;
import ua.foxminded.university.service.dto.response.UserAccountResponse;
import ua.foxminded.university.service.mapper.CourseMapper;
import ua.foxminded.university.service.mapper.UserUpdateMapper;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private StudentAccountService studentAccountService;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private UserUpdateMapper userUpdateMapper;

    @InjectMocks
    private UserAccountServiceImpl userAccountService;

    private UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("TestStudent", "Doe", "test@", "1234",
            "1234", RegistrationStatus.NEW, new HashSet<>());

    private UserAccount userAccount = new UserAccount("John", "Doe", "tests@", "1234", "1234");

    @Test
    void verifyUseMethodRegister() throws ValidationException {
        userAccountService.register(userRegistrationRequest);

        verify(userValidator).validate(userRegistrationRequest);
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodGetUserById() {
        UserAccount userAccount = new UserAccount("Jane", "Does", "dtestMail@gmail.com", "1234", "1234");
        UserAccountResponse userAccountResponse = UserAccountResponse.builder()
                .firstName("Jane")
                .lastName("Does")
                .email("dtestMail@gmail.com")
                .password("1234")
                .passwordCheck("1234")
                .build();

        when(userAccountRepository.getUserByEmail("dtestMail@gmail.com")).thenReturn(Optional.of(userAccount));
        when(userUpdateMapper.transformUserAccountToDtoResponse(userAccount)).thenReturn(userAccountResponse);

        assertEquals(userAccount.getFirstName(), userAccountService.getUserByEmail("dtestMail@gmail.com").getFirstName());
        verify(userAccountRepository).getUserByEmail("dtestMail@gmail.com");
    }

    @Test
    void verifyUseMethodUpdateUserData() {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", "", "", "", "");

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccountService.updateUserData(userAccountUpdateRequest);

        userRegistrationRequest.setFirstName("John");

        assertEquals(userRegistrationRequest.getFirstName(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getFirstName());
        verify(userValidator).validateUserId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
    }

    @Test
    void verifyUseMethodUpdateUserRoleUser() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        Role roleUser = new Role("LDG69439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.USER, 1);

        roles.add(roleUser);

        when(roleRepository.findByRole(RoleModel.USER)).thenReturn(roleUser);
        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccount.setRoles(roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "USER");

        userRegistrationRequest.setRoles(roles);

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodUpdateUserRoleStudent() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT, 1);

        roles.add(roleStudent);

        when(roleRepository.findByRole(RoleModel.STUDENT)).thenReturn(roleStudent);
        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccount.setRoles(roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "STUDENT");

        userRegistrationRequest.setRoles(roles);

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodUpdateUserRoleModerator() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        Role roleModerator = new Role("64TR9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.MODERATOR, 1);

        roles.add(roleModerator);

        when(roleRepository.findByRole(RoleModel.MODERATOR)).thenReturn(roleModerator);
        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccount.setRoles(roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "MODERATOR");

        userRegistrationRequest.setRoles(roles);

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodUpdateUserRoleAdmin() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        Role roleAdmin = new Role("54RG9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.ADMIN, 1);

        roles.add(roleAdmin);

        when(roleRepository.findByRole(RoleModel.ADMIN)).thenReturn(roleAdmin);
        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccount.setRoles(roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "ADMIN");

        userRegistrationRequest.setRoles(roles);

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodUpdateUserRoleTeacher() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        Role roleTeacher = new Role("PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.TEACHER, 1);

        roles.add(roleTeacher);

        when(roleRepository.findByRole(RoleModel.TEACHER)).thenReturn(roleTeacher);
        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccount.setRoles(roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "TEACHER");

        userRegistrationRequest.setRoles(roles);

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodUpdateEmptyRolesTest() {
        UserAccount userAccount = new UserAccount();

        when(userAccountRepository.findById(anyString())).thenReturn(Optional.of(userAccount));

        List<String> userRoles = Arrays.asList("roleId1", "roleId2", "roleId3");
        when(roleRepository.getUserRoles(anyString())).thenReturn(userRoles);

        UserAccountUpdateRequest userAccountUpdateRequest = mock(UserAccountUpdateRequest.class);

        when(userAccountUpdateRequest.getUserId()).thenReturn("userId");

        userAccountService.updateUserRoles(userAccountUpdateRequest, "USER,TEACHER");

        verify(userAccountUpdateRequest, never()).setRoles(any());
        verify(roleRepository, never()).insertNewRoles(anyString(), anyString());
    }

    @Test
    void verifyUseMethodFindUserById() {
        Set<Role> roles = new HashSet<>();

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT, 1);

        UserAccount userAccountJane = new UserAccount("Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS");
        userAccountJane.setUserId("33c99439-aaf0-4ebd-a07a-bd0c550d2311");
        userAccountJane.setRoles(roles);
        userAccountJane.setRegistrationStatus(RegistrationStatus.NEW);

        roles.add(roleStudent);

        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", roles, RegistrationStatus.NEW, 1);

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d2311")).thenReturn(Optional.of(userAccountJane));
        when(userUpdateMapper.transformUserAccountToDtoResponse(userAccountJane)).thenReturn(userAccountResponse);

        assertEquals(userAccountResponse, userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550d2311"));
        verify(userAccountRepository).findById("33c99439-aaf0-4ebd-a07a-bd0c550d2311");
    }

    @Test
    void shouldReturnListOfUsersWhenUseMethodFindAllUsersTest() {
        HashSet<CourseRequest> listCoursesRequest = new HashSet<>();
        List<UserAccount> userAccountList = new ArrayList<>();

        StudentAccountRequest studentAccountResponse = new StudentAccountRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", listCoursesRequest, 1);

        CourseRequest testCourseMath = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        CourseRequest testCourseBiology = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        listCoursesRequest.add(testCourseMath);
        listCoursesRequest.add(testCourseBiology);

        HashSet<Course> courses = courseMapper.transformHashSetCourseFromDtoRequest(listCoursesRequest);

        StudentAccount testStudent = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net", null, null,
                RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", 1);

        courseMapper.transformHashSetCourseToDtoResponse(courses);

        testStudent.setCourses(courses);

        userAccountList.add(testStudent);

        studentAccountService.addStudentCourse(studentAccountResponse, "1d95bc79-a549-4d2c-aeb5-3f929aee0f22");
        studentAccountService.addStudentCourse(studentAccountResponse, "1d95bc79-a549-4d2c-aeb5-3f929aee1234");

        when(userAccountRepository.findAll()).thenReturn(userAccountList);

        assertEquals(userUpdateMapper.transformListUserAccountToDtoResponse(userAccountList), userAccountService.findAllUsers());
    }

    @Test
    void verifyUseMethodUpdateUserDataEmailTest() {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e2", "", "", "", "", "");

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e2")).thenReturn(Optional.ofNullable(userAccount));

        userAccountUpdateRequest.setEmail("tests@");

        userAccountService.updateUserData(userAccountUpdateRequest);

        assertEquals(userAccountUpdateRequest.getEmail(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e2").get().getEmail());
        verify(userValidator).validateEmail("tests@");
        verify(userValidator).validate(userAccountUpdateRequest);
    }

    @Test
    void verifyUseMethodUpdateUserDataWhenDataIsOkTest() {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e2", "John", "", "", "", "");

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e2")).thenReturn(Optional.ofNullable(userAccount));

        userAccountService.updateUserData(userAccountUpdateRequest);

        assertEquals(userAccountUpdateRequest.getFirstName(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e2").get().getFirstName());
        verify(userValidator).validateUserId("33c99439-aaf0-4ebd-a07a-bd0c550db4e2");
        verify(userValidator).validate(userAccountUpdateRequest);
    }

    @Test
    void verifyUseMethodUpdateUserDataLastNameTest() {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e2", "John", "", "", "1234", "1234");

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e2")).thenReturn(Optional.ofNullable(userAccount));

        userAccountUpdateRequest.setLastName("Joe");

        userAccountService.updateUserData(userAccountUpdateRequest);

        assertEquals(userAccountUpdateRequest.getLastName(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e2").get().getLastName());
        verify(userValidator).validateUserId("33c99439-aaf0-4ebd-a07a-bd0c550db4e2");
        verify(userValidator).validate(userAccountUpdateRequest);
    }

    @Test
    void shouldThrowNotFoundExceptionWhereUseUpdateUserRolesTest() {
        when(userAccountRepository.findById(anyString())).thenReturn(Optional.empty());

        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("userId", "ADMIN,TEACHER", new HashSet<>());

        assertThrows(EntityNotFoundException.class, () -> {
            userAccountService.updateUserRoles(userAccountUpdateRequest, "ADMIN,TEACHER");
        });
    }
}
