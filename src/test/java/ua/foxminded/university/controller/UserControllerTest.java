package ua.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.UserAccountService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registrationPageTest() throws Exception {
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void registrationMethodTest() throws Exception {
        mockMvc.perform(post("/registration")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "dor@ukr.com")
                        .param("password", "1234")
                        .param("passwordCheck", "1234"))
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    public void shouldReturnUserEditPageTest() throws Exception {
        mockMvc.perform(get("/user/editData/33c99439-aaf0-4ebd-a07a-bd0c550db4e1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void shouldRedirectToLogoutWhenEditEmailMethodTest() throws Exception {
        mockMvc.perform(post("/user/editData/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "dor@ukr.com")
                        .param("password", "1234")
                        .param("passwordCheck", "1234"))
                .andDo(print())
                .andExpect(redirectedUrl("/logout"));
    }

    @Test
    @WithMockUser
    public void shouldRedirectToUserWhenUpdatedDataMethodTest() throws Exception {
        mockMvc.perform(post("/user/editData/33c99439-aaf0-4ebd-a07a-bd0c550db4e1")
                        .param("firstName", "John")
                        .param("lastName", "Die")
                        .param("email", "")
                        .param("password", "1234")
                        .param("passwordCheck", "1234"))
                .andDo(print())
                .andExpect(redirectedUrl("/user"));
    }
}
