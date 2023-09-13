package ua.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.UserAccountService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerPageTest {

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void userPageTest() throws Exception {
        mockMvc.perform(get("/user")
                        .param("userId", "33c99439-aaf0-4ebd-a07a-bd0c550d2311"))
                        .andDo(print())
                        .andExpect(status().isOk());
    }
}
