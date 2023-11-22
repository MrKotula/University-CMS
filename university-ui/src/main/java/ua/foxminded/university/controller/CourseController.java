package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.StudentAccountService;

@Controller
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final StudentAccountService studentAccountService;
    private final DateService dateService;

    @GetMapping("/course/info/{courseId}")
    public String viewCourseInfo(@PathVariable String courseId, Model model) {
        model.addAttribute("course", courseService.getCourseById(courseId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "course/coursePageInfo";
    }

    @PostMapping("/course/info/{courseId}")
    public String enrollStudentACourse(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String courseId) {
        studentAccountService.addStudentCourse(studentAccountService.getStudentByEmail(userDetails.getUsername()), courseId);

        return "redirect:/student/info/" + studentAccountService.getStudentByEmail(userDetails.getUsername()).getUserId();
    }
}
