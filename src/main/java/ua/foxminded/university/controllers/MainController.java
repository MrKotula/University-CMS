package ua.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MainController {
    
    @GetMapping("/")
    public String index(Model model) {
	model.addAttribute("title", "Main page");
	return "index";
    }
}
