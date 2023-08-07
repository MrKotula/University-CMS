package ua.foxminded.university.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.AllArgsConstructor;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.validator.exception.ValidationException;

@Controller
@AllArgsConstructor
@Log4j2
public class UserController {

    private final UserAccountService userAccountService;

    @GetMapping("/registration")
    public String registration(@ModelAttribute UserRegistrationRequest userRegistrationRequest, Model model) {
        model.addAttribute("userRegRequest", userRegistrationRequest);

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
        userAccountService.register(userRegistrationRequest);
        log.warn("Created new UserAccount! " + userRegistrationRequest.toString());

        return "redirect:/login";
    }
}
