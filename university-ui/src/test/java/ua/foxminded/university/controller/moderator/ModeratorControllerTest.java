package ua.foxminded.university.controller.moderator;

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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.ScheduleService;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.service.dto.request.ScheduleRequestBody;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
import ua.foxminded.university.validator.ScheduleValidator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WithMockUser(value = "moderator@user", authorities = "MODERATOR")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {ModeratorControllerTest.Initializer.class})
@Testcontainers
class ModeratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private TeacherAccountService teacherAccountService;

    @MockBean
    private DateService dateService;

    @MockBean
    private ScheduleValidator scheduleValidator;

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
    void openModeratorPageTest() throws Exception {
        mockMvc.perform(get("/user/moderator")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void openCreateSchedulePageTest() throws Exception {
        mockMvc.perform(get("/user/moderator/schedule")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void openEditSchedulePageTest() throws Exception {
        mockMvc.perform(get("/user/moderator/schedule/edit")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegisterNewScheduleTest() throws Exception {
        ScheduleRequestBody scheduleRequestBody = ScheduleRequestBody.builder()
                .selectedTeacherId("teacherId")
                .selectedGroupId("groupId")
                .selectedCourseId("courseId")
                .selectedStartLecture("08:30")
                .selectedEndLecture("09:30")
                .selectedDateOfLecture("2023-01-01")
                .selectedLectureRoom("testRoom")
                .build();

        ScheduleResponse mockScheduleResponse = new ScheduleResponse();

        when(scheduleService.createSchedule(anyString(), anyString(), anyString())).thenReturn(mockScheduleResponse);

        mockMvc.perform(post("/user/moderator/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(scheduleRequestBody)))
                .andExpect(status().isOk())
                .andExpect(view().name("moderator_panel/createSchedulePage"))
                .andExpect(model().attributeDoesNotExist("errorMessage"));

        verify(scheduleService, times(1)).register(any());
        verify(scheduleValidator, times(1)).checkAvailableLectorRoom(any());
        verify(scheduleValidator, times(1)).checkAvailableTeacher(any());
        verify(scheduleValidator, times(1)).checkAvailableGroup(any());
    }
}
