package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.updateData.UserAccountUpdateRequest;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.UserAccountRepository;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@ContextConfiguration(initializers = {UserAccountServiceImplTest.Initializer.class})
@Testcontainers
class UserAccountServiceImplTest {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StudentAccountService studentAccountService;

    @Autowired
    CourseRepository courseRepository;


    UserRegistrationRequest testStudent = new UserRegistrationRequest("TestStudent", "Doe", "rage@com", "1234",
            "1234", RegistrationStatus.NEW, new HashSet<>());

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("integration-tests-db").withUsername("sa").withPassword("sa");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    @Transactional
    void verifyUseMethodRegister() throws ValidationException {
        userAccountService.register(testStudent);

        assertEquals(testStudent.getFirstName(), userAccountRepository.findAll().get(3).getFirstName());
    }

    @Test
    @Transactional
    void verifyUseMethodGetUserById() {
        UserAccount userAccount = new UserAccount("Jane", "Does", "dtestMail@gmail.com", "1234", "1234");
        assertEquals(userAccount.getFirstName(), userAccountService.getUserByEmail("dtestMail@gmail.com").getFirstName());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateUserData() {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", "", "", "", "");
        userAccountService.updateUserData(userAccountUpdateRequest);

        testStudent.setFirstName("John");
        assertEquals(testStudent.getFirstName(), userAccountRepository.findAll().get(0).getFirstName());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateUserRoleUser() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);

        userAccountService.updateUserRoles(userAccountUpdateRequest, "USER");

        Role roleUser = roleRepository.findByRole(RoleModel.USER);

        roles.add(roleUser);

        testStudent.setRoles(roles);

        assertEquals(testStudent.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateUserRoleStudent() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);
        userAccountService.updateUserRoles(userAccountUpdateRequest, "STUDENT");

        Role roleStudent = roleRepository.findByRole(RoleModel.STUDENT);

        roles.add(roleStudent);

        testStudent.setRoles(roles);

        assertEquals(testStudent.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateUserRoleTeacher() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);
        userAccountService.updateUserRoles(userAccountUpdateRequest, "TEACHER");

        Role roleTeacher = roleRepository.findByRole(RoleModel.TEACHER);

        roles.add(roleTeacher);

        testStudent.setRoles(roles);

        assertEquals(testStudent.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateUserRoleModerator() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);
        userAccountService.updateUserRoles(userAccountUpdateRequest, "MODERATOR");

        Role roleModerator = roleRepository.findByRole(RoleModel.MODERATOR);

        roles.add(roleModerator);

        testStudent.setRoles(roles);

        assertEquals(testStudent.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateUserRoleAdmin() {
        Set<Role> roles = new HashSet<>();
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "", roles);
        userAccountService.updateUserRoles(userAccountUpdateRequest, "ADMIN");

        Role roleAdmin = roleRepository.findByRole(RoleModel.ADMIN);

        roles.add(roleAdmin);

        testStudent.setRoles(roles);

        assertEquals(testStudent.getRoles(), userAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getRoles());
    }

    @Test
    @Transactional
    void verifyUseMethodFindAllUsers() {
        Set<Role> studentRoles = new HashSet<>();
        Set<Role> adminRoles = new HashSet<>();
        Set<Course> coursesTest = new HashSet<>();
        Set<Course> courses = new HashSet<>();

        Role roleStudent = roleRepository.findByRole(RoleModel.STUDENT);
        Role roleAdmin = roleRepository.findByRole(RoleModel.ADMIN);
        Role roleUser = roleRepository.findByRole(RoleModel.USER);

        studentRoles.add(roleStudent);
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);

        Course testCourseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics");
        Course testCourseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology");

        coursesTest.add(testCourseBiology);
        courses.add(testCourseMath);
        courses.add(testCourseBiology);

        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com", null, null,
                RegistrationStatus.NEW, studentRoles,"3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "RT85796142");
        StudentAccount testStudent = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net", null, null,
                RegistrationStatus.NEW, studentRoles,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727");

        testStudentAccount.setCourses(coursesTest);
        testStudent.setCourses(courses);

        studentAccountService.addStudentCourse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "1d95bc79-a549-4d2c-aeb5-3f929aee1234");
        studentAccountService.addStudentCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22");
        studentAccountService.addStudentCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "1d95bc79-a549-4d2c-aeb5-3f929aee1234");

        UserAccount testUserAccount = UserAccount.userAccountBuilder()
                        .userId("11111439-aaf0-4ebd-a07a-bd0c550d2333")
                        .firstName("Admin")
                        .lastName("Admin")
                        .email("admin@")
                        .registrationStatus(RegistrationStatus.REGISTERED)
                        .roles(adminRoles)
                        .build();

        List<UserAccount> users = new ArrayList<>(Arrays.asList(testStudent, testStudentAccount, testUserAccount));

        assertEquals(users, userAccountService.findAllUsers());
    }

    @Test
    @Transactional
    void verifyUseMethodFindUserById() {
        Set<Role> roles = new HashSet<>();
        Role roleStudent = roleRepository.findByRole(RoleModel.STUDENT);

        roles.add(roleStudent);

        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", roles, RegistrationStatus.NEW );

        assertEquals(userAccountUpdateRequest, userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550d2311"));
    }
}
