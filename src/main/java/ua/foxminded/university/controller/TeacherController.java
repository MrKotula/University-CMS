package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.service.dto.updateData.TeacherAccountUpdateRequest;

@Controller
@AllArgsConstructor
@Log4j2
public class TeacherController {
    private final TeacherAccountService teacherAccountService;
    private final StudentAccountService studentAccountService;

    @GetMapping("/teacher")
    public String openInfoTeacherPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("teacherUpdateRequest", teacherAccountService.getTeacherByEmail(userDetails.getUsername()));

        return "/teacher/teacherPanel";
    }

    @GetMapping("/teacher/students/all")
    public String openPageAboutAllStudents(Model model) {
        model.addAttribute("students", studentAccountService.findAllStudents());

        return "/teacher/allStudents";
    }

    @GetMapping("/teacher/user/{userId}")
    public String openPageAboutAllStudents(@PathVariable String userId, Model model) {
        model.addAttribute("student", studentAccountService.findStudentById(userId));

        return "/teacher/infoStudent";
    }

    @PostMapping("/teacher/user/{studentId}")
    public String viewUserData(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String studentId) {
        TeacherAccountUpdateRequest teacherAccount = teacherAccountService.getTeacherByEmail(userDetails.getUsername());

        teacherAccountService.addStudentToScienceSupervisor(teacherAccount.getUserId(), studentId);
        log.warn("Added student to Supervisor by " + teacherAccount.getEmail());

        return "redirect:/teacher";
    }
}
