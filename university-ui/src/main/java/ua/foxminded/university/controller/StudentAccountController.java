package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.StudentAccountService;

@Controller
@AllArgsConstructor
@Log4j2
public class StudentAccountController {

    private final StudentAccountService studentAccountService;
    private final DateService dateService;

    @GetMapping("/student/info/{userId}")
    public String openStudentPage(@PathVariable String userId, Model model) {
        model.addAttribute("studentResponse", studentAccountService.findStudentById(userId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "student/infoStudent";
    }
}
