package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.request.StudentAccountRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.mapper.CourseMapper;
import ua.foxminded.university.service.mapper.RoleMapper;
import ua.foxminded.university.service.mapper.StudentAccountMapper;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.StudentValidator;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
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

    @Mock
    private StudentAccountMapper studentAccountMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private GroupValidator groupValidator;

    @InjectMocks
    private StudentAccountServiceImpl studentAccountService;

    private StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net", null, null,
           RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", 1);

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
        StudentAccountRequest studentAccountRequest = StudentAccountRequest.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .firstName("John")
                .lastName("Doe")
                .email("testemail@ukr.net")
                .registrationStatus(RegistrationStatus.NEW)
                .courses(new HashSet<>())
                .groupId("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2")
                .build();

        when(studentAccountMapper.transformStudentAccountFromDto(studentAccountRequest)).thenReturn(testStudentAccount);

        studentAccountService.updateEmail(studentAccountRequest);

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateEmail("testemail@ukr.net");
        verify(studentAccountRepository).save(any(StudentAccount.class));
    }

    @Test
    void verifyUseMethodUpdatePassword() {
        StudentAccountRequest studentAccountRequest = StudentAccountRequest.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .firstName("John")
                .lastName("Doe")
                .password("1234")
                .passwordCheck("1234")
                .email("testemail@ukr.net")
                .registrationStatus(RegistrationStatus.NEW)
                .courses(new HashSet<>())
                .groupId("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2")
                .build();

        when(studentAccountMapper.transformStudentAccountFromDto(studentAccountRequest)).thenReturn(testStudentAccount);

        studentAccountService.updatePassword(studentAccountRequest);

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
        CourseRequest courseMath = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        CourseRequest courseBiology = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<CourseRequest> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccountRequest studentAccountRequest = new StudentAccountRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses, 1);

        studentAccountService.addStudentCourse(studentAccountRequest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateCourseId("1d95bc79-a549-4d2c-aeb5-3f929aee0096");
        verify(studentValidator).validateMaxAvailableCourses(studentAccountRequest);
        verify(studentValidator).validateAvailableCourses(studentAccountRequest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");
    }

    @Test
    void verifyUseMethodAndAddStudentCourseTest() {
        CourseRequest courseMath = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        CourseRequest courseBiology = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<CourseRequest> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccountRequest studentAccountRequest = new StudentAccountRequest("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", courses, 1);

        doReturn(true).when(studentValidator).validateAvailableCourses(studentAccountRequest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        studentAccountService.addStudentCourse(studentAccountRequest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateCourseId("1d95bc79-a549-4d2c-aeb5-3f929aee0096");
        verify(studentValidator).validateMaxAvailableCourses(studentAccountRequest);
        verify(studentValidator).validateAvailableCourses(studentAccountRequest, "1d95bc79-a549-4d2c-aeb5-3f929aee0096");
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

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT, 1);

        roles.add(roleStudent);

        StudentAccount studentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, roles, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);

        studentAccount.setStudentCard("DT94381727");
        studentAccount.setCourses(courses);

        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.of(studentAccount));

        studentAccountService.findStudentById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

        verify(studentAccountRepository).findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
    }

    @Test
    void verifyUseMethodGetStudentByEmailTest() {
        Set<Role> roles = new HashSet<>();

        Role roleStudent = new Role("98LD9439-aaf0-4ebd-a07a-bd0c550db4e1", RoleModel.STUDENT, 1);

        roles.add(roleStudent);

        Course courseMath = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", 30);
        Course courseBiology = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee1234", "Biology", "course of Biology", 30);

        HashSet<Course> courses = new HashSet<>(Arrays.asList(courseMath, courseBiology));

        StudentAccount studentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, roles, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);

        studentAccount.setStudentCard("DT94381727");
        studentAccount.setCourses(courses);

        StudentAccountResponse studentAccountResponseTest = StudentAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .firstName("John")
                .lastName("Doe")
                .email("dis@ukr.net")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(roleMapper.transformSetRoleResponseFromSetRole(roles))
                .registrationStatus(RegistrationStatus.NEW)
                .groupId("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2")
                .studentCard("DT94381727")
                .courses(courseMapper.transformHashSetCourseToDtoResponse(courses))
                .build();

        when(studentAccountRepository.getStudentByEmail("dis@ukr.net")).thenReturn(studentAccount);
        when(studentAccountMapper.transformStudentAccountToDto(studentAccount)).thenReturn(studentAccountResponseTest);

        assertEquals(studentAccountResponseTest, studentAccountService.getStudentByEmail("dis@ukr.net"));
        verify(studentAccountRepository).getStudentByEmail("dis@ukr.net");
    }

    @Test
    void shouldUpdateStudentCardAndGroupDataByModeratorTest() {
        StudentAccount studentAccountTest = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);

        StudentAccountResponse studentAccountResponseTest = StudentAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .firstName("John")
                .lastName("Doe")
                .email("dis@ukr.net")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .registrationStatus(RegistrationStatus.NEW)
                .groupId("3c01e6f1-762e-43b8-a6e1-7cf493ce9233")
                .studentCard("GD94381727")
                .build();

        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.of(studentAccountTest));

        studentAccountService.updateStudentCardAndGroupDataByModerator(studentAccountResponseTest);

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateForSpecialStudentCardPattern("GD94381727");
        verify(groupValidator).validateGroupId("3c01e6f1-762e-43b8-a6e1-7cf493ce9233");
        assertEquals("GD94381727", studentAccountTest.getStudentCard());
        assertEquals("3c01e6f1-762e-43b8-a6e1-7cf493ce9233", studentAccountTest.getGroupId());
    }

    @Test
    void shouldNotUpdateStudentCardAndGroupDataByModeratorTest() {
        StudentAccount studentAccountTest = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);
        studentAccountTest.setStudentCard("DT94381727");

        StudentAccountResponse studentAccountResponseTest = mock(StudentAccountResponse.class);

        when(studentAccountResponseTest.getUserId()).thenReturn("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        when(studentAccountResponseTest.getStudentCard()).thenReturn("");
        when(studentAccountResponseTest.getGroupId()).thenReturn("");
        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.of(studentAccountTest));

        studentAccountService.updateStudentCardAndGroupDataByModerator(studentAccountResponseTest);

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        assertEquals("DT94381727", studentAccountTest.getStudentCard());
        assertEquals("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", studentAccountTest.getGroupId());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateStudentCardAndGroupDataByModeratorTest() {
        String expectedMessage = "StudentAccount not found";

        StudentAccount studentAccountTest = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);
        studentAccountTest.setStudentCard("DT94381727");

        StudentAccountResponse studentAccountResponseTest = mock(StudentAccountResponse.class);

        when(studentAccountResponseTest.getUserId()).thenReturn("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentAccountService.updateStudentCardAndGroupDataByModerator(studentAccountResponseTest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateStudentDataTest() {
        String expectedMessage = "StudentAccount not found";

        StudentAccount studentAccountTest = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);
        studentAccountTest.setStudentCard("DT94381727");

        StudentAccountResponse studentAccountResponseTest = mock(StudentAccountResponse.class);

        when(studentAccountResponseTest.getUserId()).thenReturn("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentAccountService.updateStudentData(studentAccountResponseTest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldUpdateStudentDataTest() {
        StudentAccount studentAccountTest = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);
        studentAccountTest.setStudentCard("DT94381727");

        StudentAccountResponse studentAccountResponseTest = StudentAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .firstName("Like")
                .lastName("Test")
                .email("testMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .registrationStatus(RegistrationStatus.NEW)
                .groupId("3c01e6f1-762e-43b8-a6e1-7cf493ce4565")
                .studentCard("GD94381727")
                .build();

        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.of(studentAccountTest));

        studentAccountService.updateStudentData(studentAccountResponseTest);

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        verify(studentValidator).validateForSpecialStudentCardPattern("GD94381727");
        verify(groupValidator).validateGroupId("3c01e6f1-762e-43b8-a6e1-7cf493ce4565");
        assertEquals("GD94381727", studentAccountTest.getStudentCard());
        assertEquals("3c01e6f1-762e-43b8-a6e1-7cf493ce4565", studentAccountTest.getGroupId());
        assertEquals("Like", studentAccountTest.getFirstName());
        assertEquals("3c01e6f1-762e-43b8-a6e1-7cf493ce4565", studentAccountTest.getGroupId());
    }

    @Test
    void shouldNotUpdateStudentDataTest() {
        StudentAccount studentAccountTest = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                RegistrationStatus.NEW, new HashSet<>(), "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", 1);
        studentAccountTest.setStudentCard("DT94381727");

        StudentAccountResponse studentAccountResponseTest = mock(StudentAccountResponse.class);

        when(studentAccountResponseTest.getUserId()).thenReturn("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        when(studentAccountResponseTest.getStudentCard()).thenReturn("");
        when(studentAccountResponseTest.getGroupId()).thenReturn("");
        when(studentAccountResponseTest.getFirstName()).thenReturn("");
        when(studentAccountResponseTest.getLastName()).thenReturn("");
        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(Optional.of(studentAccountTest));

        studentAccountService.updateStudentData(studentAccountResponseTest);

        verify(studentValidator).validateStudentId("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");
        assertEquals("DT94381727", studentAccountTest.getStudentCard());
        assertEquals("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", studentAccountTest.getGroupId());
        assertEquals("John", studentAccountTest.getFirstName());
        assertEquals("Doe", studentAccountTest.getLastName());
    }
}
