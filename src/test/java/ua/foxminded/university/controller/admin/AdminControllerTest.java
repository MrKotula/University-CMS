package ua.foxminded.university.controller.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.RoleService;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.service.dto.updateData.UserAccountUpdateRequest;
import java.util.HashSet;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(value = "admin@user", roles = "ADMIN")
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private CourseService courseService;

    @Test
    public void adminPageTest() throws Exception {
        mockMvc.perform(get("/admin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnGetAllUsersPageTest() throws Exception {
        mockMvc.perform(get("/admin/users/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUserEditPageTest() throws Exception {
        mockMvc.perform(get("/admin/user/editData/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUserPageTest() throws Exception {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW );

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountUpdateRequest);

        mockMvc.perform(get("/admin/user/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUserEditRolePageTest() throws Exception {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW );

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountUpdateRequest);

        mockMvc.perform(get("/admin/user/editRole/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateDataWhenEditEmailMethodTest() throws Exception {
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW );

        when(userAccountService.findUserById("33c99439-aaf0-4ebd-a07a-bd0c550db4e1")).thenReturn(userAccountUpdateRequest);

        mockMvc.perform(post("/admin/user/editData/33c99439-aaf0-4ebd-a07a-bd0c550d2311")
                        .param("lastName", "Dir")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/admin/user/33c99439-aaf0-4ebd-a07a-bd0c550d2311"));
    }

    @Test
    public void shouldUpdateUserRolesTest() throws Exception {
        mockMvc.perform(post("/admin/user/editRole/33c99439-aaf0-4ebd-a07a-bd0c550d2311")
                        .param("roles", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/admin/user/33c99439-aaf0-4ebd-a07a-bd0c550d2311"));
    }
}
