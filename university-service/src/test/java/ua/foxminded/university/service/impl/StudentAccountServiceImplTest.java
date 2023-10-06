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
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentAccountService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ContextConfiguration(initializers = {StudentAccountServiceImplTest.Initializer.class})
@Testcontainers
class StudentAccountServiceImplTest {

    @Autowired
    StudentAccountService studentAccountService;

    @Autowired
    StudentAccountRepository studentAccountRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RoleRepository roleRepository;

    StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net", null, null,
           RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727");

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
        studentAccountService.register(new UserRegistrationRequest("John", "Doe", "testemail@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>()));

        assertEquals(testStudentAccount.getFirstName(), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1").get().getFirstName());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateEmail() throws ValidationException {
        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net", null, null,
                RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");
        studentAccountService.updateEmail(testStudentAccount);

        assertEquals(Optional.of(testStudentAccount), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void verifyUseMethodUpdatePassword() {
        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net",
                "1234", "1234", RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");
        studentAccountService.updatePassword(testStudentAccount);

        assertEquals(Optional.of(testStudentAccount), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void verifyUseMethodUChangeGroup() {
        Course courseMath = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22").get();
        Course courseBiology = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee1234").get();

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        testStudentAccount.setCourses(courses);
        testStudentAccount.setGroupId("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

        studentAccountService.changeGroup("3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

        assertEquals(Optional.of(testStudentAccount), studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void shouldReturnListOfStudentsWhenUseGetStudentsWithCourseName() {
        Course courseMath = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22").get();
        Course courseBiology = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee1234").get();

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        testStudentAccount.setCourses(courses);

        List<StudentAccount> testListStudentAccount = Arrays.asList(testStudentAccount);

        assertEquals(testListStudentAccount, studentAccountService.findByCourseName("Mathematics"));
    }

    @Test
    @Transactional
    void shouldReturnListOfStudentsWhenUseRemoveStudentFromCourse() {
        List<StudentAccount> emptyList = Collections.emptyList();
        studentAccountService.removeStudentFromCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1",
                "1d95bc79-a549-4d2c-aeb5-3f929aee0f22");

        assertEquals(emptyList, studentAccountService.findByCourseName("Mathematics"));
    }

    @Test
    @Transactional
    void verifyUseMethodWhenUseInsertSaveAndAddStudentCourse() {
        Course courseMath = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22").get();
        Course courseBiology = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee1234").get();

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        testStudentAccount.setCourses(courses);

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        List<StudentAccount> testListStudentAccount = Arrays.asList(testStudentAccount);

        studentAccountRepository.save(new StudentAccount("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "John", "Doe", "dis@ukr.net",
                "123140", "123140", RegistrationStatus.NEW, new HashSet<>()));
        studentAccountService.addStudentCourse(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        assertEquals(testListStudentAccount, studentAccountService.findByCourseName("Drawing"));
    }

    @Test
    @Transactional
    void verifyUseMethodWhenUseFindAllStudentsTest() {
        assertEquals(testStudentAccount, studentAccountService.findAllStudents().get(0));
    }

    @Test
    @Transactional
    void shouldReturnStudentAccountResponseWhenUseFindStudentByIdTest() {
        Course courseMath = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22").get();
        Course courseBiology = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee1234").get();
        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        Set<Role> roles = new HashSet<>();
        Role roleStudent = roleRepository.findByRole(RoleModel.STUDENT);

        roles.add(roleStudent);

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                roles, RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        assertEquals(studentAccountResponseTest, studentAccountService.findStudentById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    @Test
    @Transactional
    void verifyUseMethodGetStudentByEmailTest() {
        Set<Role> roles = new HashSet<>();
        Role roleStudent = roleRepository.findByRole(RoleModel.STUDENT);

        Course courseMath = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22").get();
        Course courseBiology = courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee1234").get();
        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        roles.add(roleStudent);

        List<StudentAccount> listOfStudents = new ArrayList<>();

        listOfStudents.add(testStudentAccount);

        StudentAccountResponse studentAccountResponseTest = StudentAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .firstName("John")
                .lastName("Doe")
                .email("dis@ukr.net")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(roles)
                .registrationStatus(RegistrationStatus.NEW)
                .groupId("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2")
                .studentCard("DT94381727")
                .courses(courses)
                .build();

        assertEquals(studentAccountResponseTest, studentAccountService.getStudentByEmail("dis@ukr.net"));
    }
}
