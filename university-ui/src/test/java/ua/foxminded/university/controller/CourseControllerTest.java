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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.ScheduleRepository;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ContextConfiguration(initializers = {CourseControllerTest.Initializer.class})
@Testcontainers
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentAccountService studentAccountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScheduleRepository scheduleRepository;

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
    void shouldReturnCourseInfoPageTest() throws Exception {
        CourseResponse courseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", new ArrayList<>());

        when(courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22")).thenReturn(courseResponse);

        mockMvc.perform(get("/course/info/1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .param("courseId", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectAfterEnrollStudentToCourseTest() throws Exception {
        CourseResponse courseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", new ArrayList<>());
        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                new HashSet<>(), RegistrationStatus.NEW,"3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", new HashSet<>(), 1);

        when(courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22")).thenReturn(courseResponse);
        when(studentAccountService.getStudentByEmail(anyString())).thenReturn(studentAccountResponseTest);

        mockMvc.perform(post("/course/info/1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .param("courseId", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/student/info/33c99439-aaf0-4ebd-a07a-bd0c550db4e1"));
    }

    //this is temporary test
    @Test
    void testFetchSchedulesWithAssociatedEntitiesEagerly() {
        List<Schedule> schedules = scheduleRepository.findAllWithAssociatedEntities();

        //i have 7 records in base;
        assertEquals(3, schedules.size());
    }
}
