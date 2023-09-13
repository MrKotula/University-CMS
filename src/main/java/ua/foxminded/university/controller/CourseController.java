package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.university.service.CourseService;

@Controller
@AllArgsConstructor
public class CourseController {
    private CourseService courseService;

    @GetMapping("/course/info/{courseId}")
    public String viewCourseInfo(@PathVariable String courseId, Model model) {
        model.addAttribute("course", courseService.getCourseById(courseId));

        return "course/coursePageInfo";
    }
}
