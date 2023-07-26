package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.StudentRepository;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentService;

@SpringBootTest
@ContextConfiguration(initializers = { StudentServiceImplTest.Initializer.class })
@Testcontainers
class StudentServiceImplTest {

    @Autowired
    StudentService studentService;
    
    @Autowired
    StudentRepository studentRepository;
    
    Student testStudent = new Student("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", null, null, null, Status.STUDENT, RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");

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
	studentService.register(new UserRegistrationRequest("John", "Doe", "testemail@ukr.net", "12345678", "12345678", Status.STUDENT, RegistrationStatus.NEW));

	assertEquals(Optional.of(testStudent), studentRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }
    
    @Test
    @Transactional
    void verifyUseMethodRegisterWithStudentAttribute() throws ValidationException {
	UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest("John", "Dou", "test@email", "pass", "pass", Status.STUDENT, RegistrationStatus.NEW);
	studentService.register(userRegistrationRequest);

	assertEquals(testStudent.getFirstName(), studentRepository.findAll().get(2).getFirstName());
    }
    
    @Test
    @Transactional
    void verifyUseMethodUpdateEmail() throws ValidationException {
	Student testStudent = new Student("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net", null, null, 
		Status.STUDENT,	RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");
	studentService.updateEmail(testStudent);

	assertEquals(Optional.of(testStudent), studentRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }
    
    @Test
    @Transactional
    void verifyUseMethodUpdatePassword() {
	Student testStudent = new Student("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "testemail@ukr.net", "1234", "1234", 
		Status.STUDENT, RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2");
	studentService.updatePassword(testStudent);

	assertEquals(Optional.of(testStudent), studentRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }
    
    @Test
    @Transactional
    void verifyUseMethodUpdateStatus() {
	studentService.updateStatus(Status.STUDENT, "33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

	assertEquals(Optional.of(testStudent), studentRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }
    
    @Test
    @Transactional
    void verifyUseMethodUChangeGroup() {
	Student testStudent = new Student("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", null, "12345678", "12345678",
		Status.STUDENT, RegistrationStatus.REGISTERED, "3c01e6f1-762e-43b8-a6e1-7cf493ce5325");
	studentService.changeGroup("3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

	assertEquals(Optional.of(testStudent), studentRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }
    
    @Test
    @Transactional
    void shouldReturnListOfStudentsWhenUseGetStudentsWithCourseName() {
	List<Student> testListStudent = Arrays.asList(new Student("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", null, null, null,
		Status.STUDENT, RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2"));
	
	assertEquals(testListStudent, studentService.findByCourseName("math"));
    }

    @Test
    @Transactional
    void shouldReturnListOfStudentsWhenUseRemoveStudentFromCourse() {
	List<Student> emptyList = Collections.emptyList();
	studentService.removeStudentFromCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1",
		"1d95bc79-a549-4d2c-aeb5-3f929aee0f22");

	assertEquals(emptyList, studentService.findByCourseName("math"));
    }
    
    @Test
    @Transactional
    void verifyUseMethodWhenUseInsertSaveAndAddStudentCourse() {
	List<Student> testListStudent = Arrays.asList(testStudent);
	studentRepository.save(new Student("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "John", "Doe", "asd@sa", "123140", "123140", Status.NEW, RegistrationStatus.NEW));
	studentService.addStudentCourse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "1d95bc79-a549-4d2c-aeb5-3f929aee0096");

	assertEquals(testListStudent, studentService.findByCourseName("drawing"));
    }

    @Test
    @Transactional
    void verifyUseMethodWhenUseDeleteById() {
	List<Student> testListStudent = Arrays.asList(new Student("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", null, null, null,
		Status.STUDENT, RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce5325"));
	studentService.deleteById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1");

	assertEquals(testListStudent, studentRepository.findAll());
    }
}
