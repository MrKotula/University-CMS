package ua.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.AllArgsConstructor;
import ua.foxminded.university.dto.UserDto;
import ua.foxminded.university.exceptions.ValidationException;
import ua.foxminded.university.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/registration")
    public String registration(@ModelAttribute UserDto userDto, Model model) {
	model.addAttribute("userDto", userDto);

	return "registration";
    }
    
    @PostMapping("/registration")
    public String registration(UserDto userDto) throws ValidationException {
	userService.register(userDto);

	return "redirect:/";
    }
}
