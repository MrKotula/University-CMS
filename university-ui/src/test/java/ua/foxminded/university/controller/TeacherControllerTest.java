package ua.foxminded.university.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.entity.enums.Degree;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "TEACHER")
@ContextConfiguration(initializers = {TeacherControllerTest.Initializer.class})
@Testcontainers
class TeacherControllerTest {

    @MockBean
    private TeacherAccountService teacherAccountService;

    @MockBean
    private StudentAccountService studentAccountService;

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

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
    void shouldReturnTeacherInfoPageTest() throws Exception {
        TeacherAccountResponse teacherAccountResponseTest = TeacherAccountResponse.builder()
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

        when(teacherAccountService.getTeacherByEmail(anyString())).thenReturn(teacherAccountResponseTest);

        mockMvc.perform(get("/teacher")
                        .param("email", "teacherMail@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllStudentsInfoPageTest() throws Exception {
        when(studentAccountService.findAllStudents()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/teacher/students"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnInfoPageAboutStudentTest() throws Exception {
        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", new HashSet<>(), 1);

        when(studentAccountService.findStudentById(anyString())).thenReturn(studentAccountResponseTest);

        mockMvc.perform(get("/teacher/students/33c99439-aaf0-4ebd-a07a-bd0c550db4e1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectToTeacherPageWhenAddedStudentTest() throws Exception {
        TeacherAccountResponse teacherAccountResponseTest = TeacherAccountResponse.builder()
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

        when(teacherAccountService.getTeacherByEmail(anyString())).thenReturn(teacherAccountResponseTest);

        mockMvc.perform(post("/teacher/students/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                        .param("studentId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1"))
                .andDo(print())
                .andExpect(redirectedUrl("/teacher"));
    }

    @Test
    @WithMockUser(authorities = "STUDENT")
    void shouldReturnErrorWhenHasAnotherRoleTest() throws Exception {
        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", new HashSet<>(), 1);

        when(studentAccountService.findStudentById(anyString())).thenReturn(studentAccountResponseTest);

        mockMvc.perform(get("/teacher/students/33c99439-aaf0-4ebd-a07a-bd0c550db4e1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
    void shouldReturnAAllTeacherCoursesPageTest() throws Exception {
        TeacherAccountResponse teacherAccountResponseTest = TeacherAccountResponse.builder()
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

        when(teacherAccountService.getTeacherByEmail(anyString())).thenReturn(teacherAccountResponseTest);

        mockMvc.perform(get("/teacher/courses")
                .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550d8799"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
    void shouldReturnTeacherCoursePageTest() throws Exception {
        CourseResponse courseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", new ArrayList<>());

        when(courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22")).thenReturn(courseResponse);

        mockMvc.perform(get("/teacher/courses/1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .param("courseId", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
    void shouldReturnTeacherCourseEditPageTest() throws Exception {
        CourseResponse courseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", new ArrayList<>());

        when(courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22")).thenReturn(courseResponse);

        mockMvc.perform(get("/teacher/courses/1d95bc79-a549-4d2c-aeb5-3f929aee0f22/edit")
                        .param("courseId", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
    void shouldUpdateCourseDescriptionTest() throws Exception {
        CourseResponse courseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", new ArrayList<>());

        when(courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22")).thenReturn(courseResponse);

        mockMvc.perform(post("/teacher/courses/1d95bc79-a549-4d2c-aeb5-3f929aee0f22/edit")
                        .param("courseId", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .param("courseDescription", "Math"))
                .andDo(print())
                .andExpect(redirectedUrl("/teacher/courses/1d95bc79-a549-4d2c-aeb5-3f929aee0f22"));
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
    void shouldThrowValidationExceptionWhenUpdateCourseDescriptionTest() throws Exception {
        CourseResponse courseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", new ArrayList<>());

        when(courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22")).thenReturn(courseResponse);
        doThrow(new ValidationException("Validation error message")).when(courseService).updateCourseDescription(courseResponse);

        mockMvc.perform(post("/teacher/courses/1d95bc79-a549-4d2c-aeb5-3f929aee0f22/edit")
                        .param("courseId", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .flashAttr("courseResponse", courseResponse))
                .andDo(print())
                .andExpect(model().attribute("errorMessage", "Validation error message"));
    }
}
