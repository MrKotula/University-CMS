package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.validator.UserValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
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

        when(userAccountRepository.getUserByEmail("dtestMail@gmail.com")).thenReturn(Optional.of(userAccount));

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

        Role roleUser = new Role("LDG69439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.USER);

        roles.add(roleUser);

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

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);

        roles.add(roleStudent);

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

        Role roleModerator = new Role("64TR9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.MODERATOR);

        roles.add(roleModerator);

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

        Role roleAdmin = new Role("54RG9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.ADMIN);

        roles.add(roleAdmin);

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

        Role roleTeacher = new Role("PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.TEACHER);

        roles.add(roleTeacher);

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccount.setRoles(roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "ADMIN");

        userRegistrationRequest.setRoles(roles);

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodUpdateUserAllRoles() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        Role roleAdmin = new Role("54RG9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.ADMIN);
        Role roleModerator = new Role("64TR9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.MODERATOR);
        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);
        Role roleTeacher = new Role("PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.TEACHER);
        Role roleUser = new Role("LDG69439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.USER);

        roles.add(roleAdmin);
        roles.add(roleModerator);
        roles.add(roleStudent);
        roles.add(roleTeacher);
        roles.add(roleUser);

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccount.setRoles(roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "ADMIN,MODERATOR,STUDENT,TEACHER,USER");

        userRegistrationRequest.setRoles(roles);

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodUpdateEmptyRolesTest() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.ofNullable(userAccount));

        userAccountService.updateUserRoles(userAccountUpdateRequest, "");

        assertEquals(userRegistrationRequest.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
        verify(userAccountRepository).save(any(UserAccount.class));
    }

    @Test
    void verifyUseMethodFindUserById() {
        Set<Role> roles = new HashSet<>();

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);

        UserAccount userAccountJane = new UserAccount("Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS");
        userAccountJane.setUserId("33c99439-aaf0-4ebd-a07a-bd0c550d2311");
        userAccountJane.setRoles(roles);
        userAccountJane.setRegistrationStatus(RegistrationStatus.NEW);

        roles.add(roleStudent);

        when(userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d2311")).thenReturn(Optional.of(userAccountJane));

        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", roles, RegistrationStatus.NEW );

        assertEquals(userAccountUpdateRequest, userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550d2311"));
        verify(userAccountRepository).findById("33c99439-aaf0-4ebd-a07a-bd0c550d2311");
    }

    @Test
    void shouldReturnListOfUsersWhenUseMethodFindAllUsersTest() {
        Set<Course> courses = new HashSet<>();
        List<UserAccount> userAccountList = new ArrayList<>();

        StudentAccountResponse studentAccountResponse = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        Course testCourseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course testCourseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        courses.add(testCourseMath);
        courses.add(testCourseBiology);

        StudentAccount testStudent = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net", null, null,
                RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727");

        testStudent.setCourses(courses);

        userAccountList.add(testStudent);

        studentAccountService.addStudentCourse(studentAccountResponse, "1d95bc79-a549-4d2c-aeb5-3f929aee0f22");
        studentAccountService.addStudentCourse(studentAccountResponse, "1d95bc79-a549-4d2c-aeb5-3f929aee1234");

        when(userAccountRepository.findAll()).thenReturn(userAccountList);

        assertEquals(userAccountList, userAccountService.findAllUsers());
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
}
