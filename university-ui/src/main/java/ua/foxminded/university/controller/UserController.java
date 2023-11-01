package ua.foxminded.university.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.AllArgsConstructor;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.validator.exception.ValidationException;

@Controller
@AllArgsConstructor
@Log4j2
public class UserController {

    private final UserAccountService userAccountService;
    private final CourseService courseService;

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

    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("courses", courseService.findAllCourses());
        model.addAttribute("userUpdateRequest", userAccountService.getUserByEmail(userDetails.getUsername()));

        return "user";
    }

    @GetMapping ("/user/editData/{userId}")
    public String userData(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute UserAccountUpdateRequest userAccountUpdateRequest, Model model) {
        model.addAttribute("userUpdateRequest", userAccountUpdateRequest);

        return "editData";
    }

    @PostMapping("/user/editData/{userId}")
    public String editUserData(UserAccountUpdateRequest userAccountUpdateRequest) {
        if (!userAccountUpdateRequest.getEmail().isEmpty()) {
            userAccountService.updateUserData(userAccountUpdateRequest);

            return "redirect:/logout";
        }

        userAccountService.updateUserData(userAccountUpdateRequest);
        log.warn("Changed data for user! " + userAccountUpdateRequest.toString());

        return "redirect:/user";
    }
}
