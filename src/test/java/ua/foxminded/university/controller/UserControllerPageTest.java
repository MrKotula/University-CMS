package ua.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.service.dto.updateData.UserAccountUpdateRequest;
import java.util.HashSet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
        UserAccountUpdateRequest userAccountUpdateRequest = new UserAccountUpdateRequest("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com",
                "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", "$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS", new HashSet<>(), RegistrationStatus.NEW );

        when(userAccountService.getUserByEmail(anyString())).thenReturn(userAccountUpdateRequest);

        mockMvc.perform(get("/user")
                        .param("userId", "3c99439-aaf0-4ebd-a07a-bd0c550d2311")
                        .param("email", "dis@ukr.net"))
                        .andDo(print())
                        .andExpect(status().isOk());
    }
}
