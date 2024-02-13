package ua.foxminded.university.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
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
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.service.ScheduleService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.response.RoleResponse;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.service.mapper.RoleMapper;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {StudentControllerTest.Initializer.class})
@Testcontainers
class StudentControllerTest {

    @MockBean
    private StudentAccountService studentAccountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ScheduleService scheduleService;

    private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

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
    @WithMockUser(authorities = "STUDENT")
    void shouldReturnStudentInfoPageTest() throws Exception {
        Set<RoleResponse> roles = new HashSet<>();

        RoleResponse roleResponseStudent = roleMapper.transformRoleResponseFromRole(roleRepository.findByRole(RoleModel.STUDENT));

        roles.add(roleResponseStudent);

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                roles, RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", new HashSet<>());

        when(studentAccountService.findStudentById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(studentAccountResponseTest);

        mockMvc.perform(get("/student/info/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldReturnErrorWhenHasAnotherRoleTest() throws Exception {
        Set<RoleResponse> roles = new HashSet<>();

        RoleResponse roleResponseUser = roleMapper.transformRoleResponseFromRole(roleRepository.findByRole(RoleModel.USER));

        roles.add(roleResponseUser);

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                roles, RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", new HashSet<>());

        when(studentAccountService.findStudentById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(studentAccountResponseTest);

        mockMvc.perform(get("/student/info/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "STUDENT")
    void shouldReturnStudentInfoScheduleTomorrowPageTest() throws Exception {
        Set<RoleResponse> roles = new HashSet<>();

        RoleResponse roleResponseStudent = roleMapper.transformRoleResponseFromRole(roleRepository.findByRole(RoleModel.STUDENT));

        roles.add(roleResponseStudent);

        StudentAccountResponse studentAccountResponseTest = new StudentAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550db4e1", "John", "Doe", "dis@ukr.net",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS",
                roles, RegistrationStatus.NEW, "3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "DT94381727", new HashSet<>());

        when(studentAccountService.findStudentById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(studentAccountResponseTest);

        mockMvc.perform(get("/student/info/33c99439-aaf0-4ebd-a07a-bd0c550db4e1/schedule/tomorrow")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
