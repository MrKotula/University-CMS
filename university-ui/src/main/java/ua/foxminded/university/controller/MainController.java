package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;
import ua.foxminded.university.service.DateService;

@Controller
@AllArgsConstructor
public class MainController {

    private final DateService dateService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "about";
    }
}
