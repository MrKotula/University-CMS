package ua.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.dto.response.CourseResponse;
import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldReturnCourseInfoPageTest() throws Exception {
        CourseResponse courseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee0f22", "Mathematics", "course of Mathematics", new ArrayList<>());

        when(courseService.getCourseById("1d95bc79-a549-4d2c-aeb5-3f929aee0f22")).thenReturn(courseResponse);

        mockMvc.perform(get("/course/info/1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .param("courseId", "1d95bc79-a549-4d2c-aeb5-3f929aee0f22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
