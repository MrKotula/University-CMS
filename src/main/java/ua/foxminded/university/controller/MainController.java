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

        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {

        return "about";
    }
}
