package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.validator.StudentValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class StudentAccountServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private StudentAccountRepository studentAccountRepository;

    @Mock
    private StudentValidator studentValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentAccountServiceImpl studentAccountService;

    private StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net", null, null,
           RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727");


    @Test
    void verifyUseMethodRegister() throws ValidationException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Doe", "testemail@ukr.net", "12345678",
                "12345678", RegistrationStatus.NEW, new HashSet<>());

        studentAccountService.register(userRegistrationRequest);

        verify(studentValidator).validateStudent(userRegistrationRequest);
        verify(studentAccountRepository).save(any(StudentAccount.class));
    }

    @Test
    void verifyUseMethodUpdateEmail() throws ValidationException {
        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net", null, null,
                RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

        studentAccountService.updateEmail(testStudentAccount);

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateEmail("testemail@ukr.net");
        verify(studentAccountRepository).save(any(StudentAccount.class));
    }

    @Test
    void verifyUseMethodUpdatePassword() {
        StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net",
                "1234", "1234", RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

        studentAccountService.updatePassword(testStudentAccount);

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentAccountRepository).save(any(StudentAccount.class));
    }

    @Test
    void verifyUseMethodUChangeGroup() {
        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        testStudentAccount.setCourses(courses);
        testStudentAccount.setGroupId("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

        studentAccountService.changeGroup("3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateGroupId("3c01e6f1-762e-43b8-a6e1-7cf493ce5325");
    }

    @Test
    void shouldReturnListOfStudentsWhenUseGetStudentsWithCourseName() {
        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        testStudentAccount.setCourses(courses);

        studentAccountService.findByCourseName("Mathematics");

        verify(studentAccountRepository).findByCourseName("Mathematics");
    }

    @Test
    void shouldReturnListOfStudentsWhenUseRemoveStudentFromCourse() {
        List<StudentAccount> emptyList = Collections.emptyList();

        studentAccountService.removeStudentFromCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22");

        assertEquals(emptyList, studentAccountService.findByCourseName("Mathematics"));
        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateCourseId("1d95bc79-a549-4d2c-aeb5-3f929aee0f22");
        verify(studentAccountRepository).removeStudentFromCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22");
    }

    @Test
    void verifyUseMethodWhenUseInsertSaveAndAddStudentCourse() {
        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        studentAccountService.addStudentCourse(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateCourseId("1d95bc79-a549-4d2c-aeb5-3f929aee0096");
        verify(studentValidator).validateMaxAvailableCourses(studentAccountResponseTest);
        verify(studentValidator).validateAvailableCourses(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");
    }

    @Test
    void verifyUseMethodAndAddStudentCourseTest() {
        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses);

        doReturn(true).when(studentValidator).validateAvailableCourses(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        studentAccountService.addStudentCourse(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateCourseId("1d95bc79-a549-4d2c-aeb5-3f929aee0096");
        verify(studentValidator).validateMaxAvailableCourses(studentAccountResponseTest);
        verify(studentValidator).validateAvailableCourses(studentAccountResponseTest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");
        verify(studentAccountRepository).addStudentCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "1d95bc79-a549-4d2c-aeb5-3f929aee0096");
    }

    @Test
    void verifyUseMethodWhenUseFindAllStudentsTest() {
        studentAccountService.findAllStudents();

        verify(studentAccountRepository).findAll();
    }

    @Test
    void shouldReturnStudentAccountResponseWhenUseFindStudentByIdTest() {
        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        Set<Role> roles = new HashSet<>();

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);

        roles.add(roleStudent);

        StudentAccount studentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, roles, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

        studentAccount.setStudentCard("DT94381727");
        studentAccount.setCourses(courses);

        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.of(studentAccount));

        studentAccountService.findStudentById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

        verify(studentAccountRepository).findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
    }

    @Test
    void verifyUseMethodGetStudentByEmailTest() {
        Set<Role> roles = new HashSet<>();

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT);

        roles.add(roleStudent);

        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccount studentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, roles, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

        studentAccount.setStudentCard("DT94381727");
        studentAccount.setCourses(courses);

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

        when(studentAccountRepository.getStudentByEmail("dis@ukr.net")).thenReturn(studentAccount);

        assertEquals(studentAccountResponseTest, studentAccountService.getStudentByEmail("dis@ukr.net"));
        verify(studentAccountRepository).getStudentByEmail("dis@ukr.net");
    }
}
