package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.foxminded.university.service.DateService;

@Controller
@AllArgsConstructor
public class LoginController {

    private final DateService dateService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "login";
    }
}
