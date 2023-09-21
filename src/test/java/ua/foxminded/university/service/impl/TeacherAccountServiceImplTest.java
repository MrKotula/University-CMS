package ua.foxminded.university.service.impl;

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
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.entity.enums.Degree;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.TeacherAccountRepository;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = {TeacherAccountServiceImplTest.Initializer.class})
@Testcontainers
class TeacherAccountServiceImplTest {

    @Autowired
    TeacherAccountService teacherAccountService;

    @Autowired
    TeacherAccountRepository teacherAccountRepository;

    @Autowired
    RoleRepository roleRepository;

    TeacherAccount teacherAccountTest = TeacherAccount.teacherBuilder()
            .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
            .firstName("Jin")
            .lastName("Tores")
            .email("teacherMail@gmail.com")
            .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
            .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
            .roles(new HashSet<>())
            .registrationStatus(RegistrationStatus.REGISTERED)
            .degree(Degree.DOCTORAL)
            .phoneNumber("067-768-874")
            .diplomaStudents(new ArrayList<>())
            .build();

    StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com", null, null,
            RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "RT85796142");

    StudentAccount testStudentAccountJohn = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net", null, null,
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
    void verifyUseMethodGetTeacherByEmailTest() {
        Set<Role> roles = new HashSet<>();
        Role roleTeacher = roleRepository.findByRole(RoleModel.TEACHER);
        Role roleUser = roleRepository.findByRole(RoleModel.USER);

        roles.add(roleTeacher);
        roles.add(roleUser);

        List<StudentAccount> listOfStudents = new ArrayList<>();

        listOfStudents.add(testStudentAccount);

        TeacherAccountResponse teacherAccountResponseTest = TeacherAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(roles)
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(listOfStudents)
                .build();

        assertEquals(teacherAccountResponseTest, teacherAccountService.getTeacherByEmail("teacherMail@gmail.com"));
    }

    @Test
    @Transactional
    void verifyUseAddStudentToScienceSupervisorWhenStudentAlreadyIsTest() {
        List<StudentAccount> listOfStudents = new ArrayList<>();

        listOfStudents.add(testStudentAccount);

        teacherAccountTest.setDiplomaStudents(listOfStudents);

        teacherAccountService.addStudentToScienceSupervisor(teacherAccountTest.getUserId(), testStudentAccount.getUserId());

        assertEquals(teacherAccountTest.getDiplomaStudents(), teacherAccountService.getTeacherByEmail("teacherMail@gmail.com").getDiplomaStudents());
    }
}
