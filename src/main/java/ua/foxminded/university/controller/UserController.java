package ua.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.AllArgsConstructor;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registration(@ModelAttribute UserRegistrationRequest userRegistrationRequest, Model model) {
        model.addAttribute("userDto", userRegistrationRequest);

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
        userService.register(userRegistrationRequest);

        return "redirect:/";
    }
}
