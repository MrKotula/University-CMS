package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "This is main page. Welcome!");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Welcome to page about us!");
        return "about";
    }
}
