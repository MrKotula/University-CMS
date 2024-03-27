package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.validator.exception.ValidationException;

@Controller
@AllArgsConstructor
@Log4j2
public class TeacherController {
    private static final String STUDENT_TEACHER_API = "/teacher/students";
    private static final String COURSE_TEACHER_API = "/teacher/courses";

    private final TeacherAccountService teacherAccountService;
    private final StudentAccountService studentAccountService;
    private final DateService dateService;
    private final CourseService courseService;

    @GetMapping("/teacher")
    public String openInfoTeacherPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("teacherUpdateRequest", teacherAccountService.getTeacherByEmail(userDetails.getUsername()));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "/teacher/teacherPanel";
    }

    @GetMapping(STUDENT_TEACHER_API)
    public String openPageAboutAllStudents(Model model) {
        model.addAttribute("students", studentAccountService.findAllStudents());
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "/teacher/allStudents";
    }

    @GetMapping(STUDENT_TEACHER_API + "/{userId}")
    public String openPageAboutInfoStudent(@PathVariable String userId, Model model) {
        model.addAttribute("student", studentAccountService.findStudentById(userId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "/teacher/infoStudent";
    }

    @PostMapping(STUDENT_TEACHER_API + "/{studentId}")
    public String viewUserData(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String studentId) {
        TeacherAccountResponse teacherAccount = teacherAccountService.getTeacherByEmail(userDetails.getUsername());

        teacherAccountService.addStudentToScienceSupervisor(teacherAccount.getUserId(), studentId);
        log.warn("Added student to Supervisor by " + teacherAccount.getEmail());

        return "redirect:/teacher";
    }

    @GetMapping(COURSE_TEACHER_API)
    public String openPageAllTeacherCourses(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("teacher", teacherAccountService.getTeacherByEmail(userDetails.getUsername()));
        model.addAttribute("courseService", courseService);
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "/teacher/allTeacherCoursesPage";
    }

    @GetMapping(COURSE_TEACHER_API + "/{courseId}")
    public String openPageTeacherCourses(@PathVariable String courseId, Model model) {
        model.addAttribute("course", courseService.getCourseById(courseId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "/teacher/courseTeacherPage";
    }

    @GetMapping(COURSE_TEACHER_API + "/{courseId}/edit")
    public String openEditTeacherCoursePage(@PathVariable String courseId, Model model) {
        model.addAttribute("course", courseService.getCourseById(courseId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "/teacher/courseTeacherEditPage";
    }

    @PostMapping(COURSE_TEACHER_API + "/{courseId}/edit")
    public String editCourseDescription(@ModelAttribute CourseResponse courseDtoRequest, Model model) {
        try {
            courseService.updateCourseDescription(courseDtoRequest);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "error_panel/errorPage";
        }

        return "redirect:" + COURSE_TEACHER_API + "/" + courseDtoRequest.getCourseId();
    }
}
