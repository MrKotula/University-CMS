package ua.foxminded.university.controller.admin;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.RoleService;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.service.dto.request.GroupRequest;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.service.dto.response.UserAccountResponse;
import ua.foxminded.university.service.mapper.GroupMapper;
import ua.foxminded.university.validator.GroupValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import java.util.HashSet;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(value = "admin@user", authorities = "ADMIN")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AdminControllerTest.Initializer.class})
@Testcontainers
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private GroupMapper groupMapper;

    @MockBean
    private GroupValidator groupValidator;

    @MockBean
    private GroupRepository groupRepository;

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
    void adminPageTest() throws Exception {
        mockMvc.perform(get("/admin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnGetAllUsersPageTest() throws Exception {
        mockMvc.perform(get("/admin/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUserEditPageTest() throws Exception {
        mockMvc.perform(get("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550db4e1/edit")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUserPageTest() throws Exception {
        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW, 1);

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountResponse);

        mockMvc.perform(get("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUserEditRolePageTest() throws Exception {
        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW, 1);

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountResponse);

        mockMvc.perform(get("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550db4e1/roles")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateDataWhenEditEmailMethodTest() throws Exception {
        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW, 1);

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountResponse);

        mockMvc.perform(post("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550d2311/edit")
                        .param("lastName", "Dir")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550d2311"));
    }

    @Test
    void shouldUpdateUserRolesTest() throws Exception {
        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW, 1);

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountResponse);

        mockMvc.perform(post("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550d2311/roles")
                        .param("roles", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550d2311"));
    }

    @Test
    @WithMockUser(authorities = "STUDENT")
    void shouldReturnErrorWhenHasRoleStudentTest() throws Exception {
        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW, 1);

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountResponse);

        mockMvc.perform(get("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldReturnErrorWhenHasRoleUserTest() throws Exception {
        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW, 1);

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountResponse);

        mockMvc.perform(get("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "TEACHER")
    void shouldReturnErrorWhenHasRoleTeacherTest() throws Exception {
        UserAccountResponse userAccountResponse = new UserAccountResponse("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW, 1);

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountResponse);

        mockMvc.perform(get("/admin/users/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnViewAllGroupsPageTest() throws Exception {
        mockMvc.perform(get("/admin/groups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnViewGroupDataPageTest() throws Exception {
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        when(groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(groupResponse);

        mockMvc.perform(get("/admin/groups/1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .param("groupId", "1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnEditGroupNamePageTest() throws Exception {
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        when(groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(groupResponse);

        mockMvc.perform(get("/admin/groups/1d95bc79-a549-4d2c-aeb5-3f929aee5432/edit")
                        .param("groupId", "1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRemoveGroupTest() throws Exception {
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        when(groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(groupResponse);

        mockMvc.perform(delete("/admin/groups/1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .param("groupId", "1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/admin/groups"));
    }

    @Test
    void shouldThrowValidationExceptionWhenRemoveGroupTest() throws Exception {
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .countStudents(5)
                .build();

        when(groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(groupResponse);
        doThrow(new ValidationException("Validation error message")).when(groupService).removeGroup("1d95bc79-a549-4d2c-aeb5-3f929aee5432");

        mockMvc.perform(delete("/admin/groups/1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .param("groupId", "1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(model().attribute("errorMessage", "Validation error message"));
    }

    @Test
    void shouldEditGroupNameWhenUseMethodEditGroupNameTest() throws Exception {
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .countStudents(5)
                .build();

        when(groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(groupResponse);

        mockMvc.perform(post("/admin/groups/1d95bc79-a549-4d2c-aeb5-3f929aee5432/edit")
                        .param("groupId", "1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/admin/groups"));
    }

    @Test
    void shouldThrowValidationExceptionWhenUseMethodUpdateGroupNameTest() throws Exception {
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43S")
                .countStudents(5)
                .build();

        GroupRequest groupRequest = GroupRequest.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43S")
                .countStudents(5)
                .build();

        when(groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(groupResponse);
        when(groupMapper.transformGroupRequestFromDtoResponse(groupResponse)).thenReturn(groupRequest);
        doThrow(new ValidationException("Group name cannot special format for group! Use like this format (GR-12)")).when(groupService).updateGroupName(groupResponse);

        mockMvc.perform(post("/admin/groups/1d95bc79-a549-4d2c-aeb5-3f929aee5432/edit")
                        .flashAttr("groupResponse", groupResponse)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(model().attribute("errorMessage", "Group name cannot special format for group! Use like this format (GR-12)"));
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionWhenRemoveGroupTest() throws Exception {
        GroupResponse groupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .countStudents(5)
                .build();

        when(groupService.getGroupById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(groupResponse);
        doThrow(new DataIntegrityViolationException("This group have a schedule. Remove schedule before remove group!")).when(groupService).removeGroup("1d95bc79-a549-4d2c-aeb5-3f929aee5432");

        mockMvc.perform(delete("/admin/groups/1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .param("groupId", "1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(model().attribute("dataIntegrityErrorMessage", "This group have a schedule. Remove schedule before remove group!"));
    }
}
