package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.foxminded.university.service.TeacherAccountService;

@Controller
@AllArgsConstructor
@Log4j2
public class TeacherController {
    private final TeacherAccountService teacherAccountService;

    @GetMapping("/teacher")
    public String openInfoTeacherPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("teacherUpdateRequest", teacherAccountService.getTeacherByEmail(userDetails.getUsername()));

        return "/teacher/teacherPanel";
    }
}
